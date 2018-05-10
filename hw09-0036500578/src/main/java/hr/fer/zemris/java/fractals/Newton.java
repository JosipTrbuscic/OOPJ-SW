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

/**
 * This program draws fractals derived from Newton-Raphson iteration. User must enter 
 * two or more roots of complex polynomial in format "a+ib" where a and b are real numbers 
 * and i is imaginary unit. Roots will be used to compute the polynomial by
 * multiplying together a series of (x−a) terms.
 * Each pixel is represented by complex number which will be tested for convergence. Convergence 
 * test stops when max number of iterations or convergence threshold is reached. If our approximation 
 * is close enough to the root pixel will be colored accordingly.
 * @author Josip Trbuscic
 *
 */
public class Newton {
	/**
	 * Complex number convergence threshold
	 */
	private static final double convergenceThreshold = 1E-3;
	
	/**
	 * Max number of Newton-Raphson iterations
	 */
	private static final int maxIter = 100;
	
	/**
	 * Comparison threshold 
	 */
	private static final double rootThreshold = 2E-3;

	/**
	 * Starting method 
	 * @param args - Command line arguments. Not used here
	 */
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
			}
			rootNumber++;
		}
		if(roots.size() < 2 ) {
			System.out.println("You must enter at least 2 roots");
			System.exit(0);
		}

		Complex[] rootsArray = new Complex[roots.size()];
		ComplexRootedPolynomial rootedPol = new ComplexRootedPolynomial(roots.toArray(rootsArray));

		FractalViewer.show(new MyProducer(rootedPol.toComplexPolynom(), rootedPol));
		sc.close();
	}

	/**
	 * Parses given string as complex number in format "a+ib"
	 * @param s - string to be parsed
	 * @return - new complex number whose real and imaginary part 
	 * 			were given in a string
	 * @throws IllegalArgumentException if string does not represent 
	 * 			complex number in a valid format
	 */
	public static Complex parse(String s) {
		Pattern p = Pattern.compile("^\\s*([+-]?\\d*\\.?\\d+)?\\s*" 
								+ "(([+-])?\\s*i(\\d*\\.?\\d+)?)\\s*$");
		Matcher m = p.matcher(s);

		if (s.trim().matches("[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?")) {
			return new Complex(Double.parseDouble(s), 0);

		} else if (m.find()) {
			if(m.group(1) != null && m.group(3) == null) {
				throw new IllegalArgumentException("Invalid complex number");
			}
			StringBuilder imSb = new StringBuilder();
			StringBuilder reSb = new StringBuilder();
			
			imSb.append(m.group(3) == null ? "+" : m.group(3));
			imSb.append(m.group(4) == null ? "1" : m.group(4));

			reSb.append(m.group(1) == null ? "0" : m.group(1));

			return new Complex(Double.parseDouble(reSb.toString()), Double.parseDouble(imSb.toString()));
		}
		throw new IllegalArgumentException("Invalid complex number");
	}

	/**
	 * Class which represents a task which will potentially
	 * be executed in a different thread. This implementation
	 * determines index of root in a list that is closest to 
	 * calculated complex number. 
	 * @author Josip Trbuscic
	 *
	 */
	public static class CalculationJob implements Callable<Void> {
		/**
		 * Minimum value for real part of complex number
		 */
		private double reMin;
		
		/**
		 * Maximum value for real part of complex number
		 */
		private double reMax;
		
		/**
		 * Minimum value for imaginary part of complex number
		 */
		private double imMin;
		
		/**
		 * Maximum value for imaginary part of complex number
		 */
		private double imMax;
		
		/**
		 * Window width
		 */
		private int width;
		
		/**
		 * Window height
		 */
		private int height;
		
		/**
		 * Minimum pixel row for this job
		 */
		private int yMin;
		
		/**
		 * Maximum pixel row for this job
		 */
		private int yMax;
		
		/**
		 * Array of indexes
		 */
		short[] data;
		
		/**
		 * Complex polynomial
		 */
		private ComplexPolynomial polynomial;
		
		/**
		 * Roots of complex polynomial
		 */
		private ComplexRootedPolynomial roots;
		
		/**
		 * Array offset
		 */
		int offset;

		/**
		 * Constructs new job
		 * @param reMin - Minimum value for real part of complex number
		 * @param reMax - Maximum value for real part of complex number
		 * @param imMin - Minimum value for imaginary part of complex number
		 * @param imMax - Maximum value for imaginary part of complex number
		 * @param width - Window width
		 * @param height - Window height
		 * @param yMin - Minimum pixel row for this job
		 * @param yMax - Maximum pixel row for this job
		 * @param data - Array of indexes
		 * @param polynomial - Complex polynomial
		 * @param roots - Array offset
		 */
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

	/**
	 * Class that represents fractal generated by 
	 * Newton-Raphsons iteration
	 * @author Josip Trbuscic
	 *
	 */
	public static class MyProducer implements IFractalProducer {
		/**
		 * Complex polynomial
		 */
		private ComplexPolynomial polynomial;

		/**
		 * Roots of complex polynomial
		 */
		private ComplexRootedPolynomial roots;

		/**
		 * Thread pool
		 */
		private ExecutorService pool;
		
		/**
		 * Constructor
		 * @param polynomial - complex polynomial
		 * @param roots - roots of complex polynomial
		 */
		public MyProducer(ComplexPolynomial polynomial, ComplexRootedPolynomial roots) {
			this.polynomial = polynomial;
			this.roots = roots;
			
			pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(),
					new ThreadFactory() {
						public Thread newThread(Runnable r) {
							Thread t = Executors.defaultThreadFactory().newThread(r);
							t.setDaemon(true);
							return t;
						}
					});
		}

		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height,
				long requestNo, IFractalResultObserver observer) {
			System.out.println("Zapocinjem izracun...");
			short[] data = new short[width * height];
			final int jobPartsCount = 8 * Runtime.getRuntime().availableProcessors();
			int heightPerJob = height / jobPartsCount;

			List<Future<Void>> results = new ArrayList<>();

			for (int i = 0; i < jobPartsCount; i++) {
				int yMin = i * heightPerJob;
				int yMax = (i + 1) * heightPerJob - 1;
				if (i == jobPartsCount - 1) {
					yMax = height - 1;
				}
				CalculationJob job = new CalculationJob(reMin, reMax, imMin, imMax, width, height, yMin, yMax, data,
						polynomial, roots);
				results.add(pool.submit(job));
			}
			for (Future<Void> resJob : results) {
				try {
					resJob.get();
				} catch (InterruptedException | ExecutionException e) {
				}
			}
			System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
			observer.acceptResult(data, (short) (polynomial.order() + 1), requestNo);
		}
	}
}
