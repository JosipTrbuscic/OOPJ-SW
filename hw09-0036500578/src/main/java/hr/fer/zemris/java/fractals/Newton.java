package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

public class Newton {
	private static final double convergenceThreshold = 1E-3;
	private static final int maxIter = 50;
	private static final double rootThreshold = 2E-3;

	public static void main(String[] args) {
		System.out.println("Welcome to Newton-Raphson " + "iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, " + "one root per line. Enter 'done' when done.");

		Scanner sc = new Scanner(System.in);
		int rootNumber = 1;
		List<Complex> roots = new ArrayList<>();

		while (true) {
			System.out.print("Root " + rootNumber + ">");
			String input = sc.nextLine();

			if (input.equals("done"))
				break;

			Complex root;
			try {
				root = parse(input);
				roots.add(root);

			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
				continue;
				//System.exit(0);
			}
			rootNumber++;
		}

		Complex[] rootsArray = new Complex[roots.size()];
		ComplexRootedPolynomial rootedPol = new ComplexRootedPolynomial(roots.toArray(rootsArray));

		FractalViewer.show(new MyProducer(rootedPol.toComplexPolynom(), rootedPol));
		sc.close();
	}

	public static Complex parse(String s) {
		Pattern p = Pattern.compile("^\\s*([+-]?\\d*\\.?\\d+)?\\s*" + "(([+-])?\\s*i(\\d*\\.?\\d+)?)\\s*$");
		Matcher m = p.matcher(s);

		if (s.trim().matches("[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?")) {
			return new Complex(Double.parseDouble(s), 0);

		} else if (m.find()) {
			StringBuilder imSb = new StringBuilder();
			StringBuilder reSb = new StringBuilder();

			imSb.append(m.group(3) == null ? "+" : m.group(3));
			imSb.append(m.group(4) == null ? "1" : m.group(4));

			reSb.append(m.group(1) == null ? "0" : m.group(1));

			return new Complex(Double.parseDouble(reSb.toString()), Double.parseDouble(imSb.toString()));
		}
		throw new IllegalArgumentException("Invalid complex number");
	}

	public static class CalculationJob implements Callable<Void> {
		double reMin;
		double reMax;
		double imMin;
		double imMax;
		int width;
		int height;
		int yMin;
		int yMax;
		short[] data;
		ComplexPolynomial polynomial;
		ComplexRootedPolynomial roots;
		int offset;

		public CalculationJob(double reMin, double reMax, double imMin, double imMax, int width, int height, int yMin,
				int yMax, short[] data, ComplexPolynomial polynomial, ComplexRootedPolynomial roots) {
			super();
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.data = data;
			this.polynomial = polynomial;
			this.roots = roots;
		}

		@Override
		public Void call() {

			System.out.println("Zapocinjem izracun...");
			ComplexPolynomial derived = polynomial.derive();
			int offset = yMin*width;
			for (int y = yMin; y <= yMax; y++) {
				for (int x = 0; x < width; x++) {
					double cre = x / (width - 1.0) * (reMax - reMin) + reMin;
					double cim = (height - 1.0 - y) / (height - 1) * (imMax - imMin) + imMin;
					Complex zn = new Complex(cre, cim);
					double module = 0;
					int iters = 0;
					Complex zn1;
					do {
						Complex numerator = polynomial.apply(zn);
						Complex denominator = derived.apply(zn);
						Complex	fraction = numerator.divide(denominator);
						zn1 = zn.sub(fraction);
						module = zn1.sub(zn).module();
						zn = zn1;
						iters++;
					} while (module > convergenceThreshold && iters < maxIter);
					int index = roots.indexOfClosestRootFor(zn1, rootThreshold);
					if (index == -1) {
						data[offset++] = 0;
					} else {
						data[offset++] = (short) (index+1);
					}
				}
			}
			return null;
		}
	}

	public static class MyProducer implements IFractalProducer {
		private ComplexPolynomial polynomial;

		private ComplexRootedPolynomial roots;

		public MyProducer(ComplexPolynomial polynomial, ComplexRootedPolynomial roots) {
			this.polynomial = polynomial;
			this.roots = roots;
		}

		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height,
				long requestNo, IFractalResultObserver observer) {
			System.out.println("Zapocinjem izracun...");
			short[] data = new short[width * height];
			final int jobPartsCount = 8 * Runtime.getRuntime().availableProcessors();
			int brojYPoTraci = height / jobPartsCount;

			// TODO: Lambda for thread factory
			ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(),
					new ThreadFactory() {
						public Thread newThread(Runnable r) {
							Thread t = Executors.defaultThreadFactory().newThread(r);
							t.setDaemon(true);
							return t;
						}
					});

			List<Future<Void>> results = new ArrayList<>();

			for (int i = 0; i < jobPartsCount; i++) {
				int yMin = i * brojYPoTraci;
				int yMax = (i + 1) * brojYPoTraci - 1;
				if (i == jobPartsCount - 1) {
					yMax = height - 1;
				}
				CalculationJob job = new CalculationJob(reMin, reMax, imMin, imMax, width, height, yMin, yMax, data,
						polynomial, roots);
				results.add(pool.submit(job));
			}
			for (Future<Void> posao : results) {
				try {
					posao.get();
				} catch (InterruptedException | ExecutionException e) {
				}
			}
			pool.shutdown();
			System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
			observer.acceptResult(data, (short) (polynomial.order() + 1), requestNo);
		}
	}
}
