package hr.fer.zemris.java.raytracer.model;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * This class represents multithreaded ray-caster program which 
 * displays 3D objects in a 2D plane using Phong 
 * reflection model. Multithreading is implemented with 
 * instances of {@link ForkJoinPool} class
 * @author Josip Trbuscic
 *
 */
public class RayCasterParallel {
	/**
	 * Starting method
	 * @param args - command line arguments. Not used here.
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(), new Point3D(10, 0, 0), new Point3D(0, 0, 0),
				new Point3D(0, 0, 10), 20, 20);
	}
	
	/**
	 * This class is implementation of a recursive task possibly 
	 * performed on multiple threads. This implementation is used 
	 * to calculate color of each pixel which is done by splitting the screen
	 * along y axis and forwarding the job to different threads until 
	 * threshold is reached.
	 * @author Josip Trbuscic
	 *
	 */
	public static class CalculationJob extends RecursiveAction{
		private static final long serialVersionUID = 1L;
		
		/**
		 * Line height measured in pixels
		 */
		private static int threshold = 16;
		
		/**
		 * Intensity of red color for each pixel
		 */
		short[] red;
		
		/**
		 * Intensity of green color for each pixel
		 */
		short[] green;
		
		/**
		 * Intensity of blue color for each pixel
		 */
		short[] blue;
		
		/**
		 * Observer vector
		 */
		Point3D eye;
		
		/**
		 * y axis of a screen
		 */
		Point3D yAxis;
		
		/**
		 * x axis of a screen
		 */
		Point3D xAxis;
		
		/**
		 * corner of a screen
		 */
		Point3D screenCorner;
		
		/**
		 * Scene
		 */
		Scene scene;
		
		/**
		 * Height lower limit 
		 */
		int yMin;
		
		/**
		 * Height upper limit 
		 */
		int yMax;
		
		/**
		 * Width of a space
		 */
		double horizontal; 
		
		/**
		 * Height of a space
		 */
		double vertical;
		
		/**
		 * Width of a window
		 */
		int width;
		/**
		 * Height of a window
		 */
		int height;
		
		/**
		 * Constructor
		 * @param red - Intensity of red color for each pixel
		 * @param green - Intensity of green color for each pixel
		 * @param blue - Intensity of blue color for each pixel
		 * @param eye - Observer vector
		 * @param yAxis - y axis of a screen
		 * @param xAxis - x axis of a screen
		 * @param screenCorner - corner of a screen
		 * @param scene - Scene
		 * @param yMin - Height lower limit 
		 * @param yMax - Height upper limit 
		 * @param horizontal - Width of a space
		 * @param vertical - Height of a space
		 * @param width - Width of a window
		 * @param height - Height of a window
		 */
		public CalculationJob(short [] red, short[] green, short[] blue,
								Point3D eye, Point3D yAxis, Point3D xAxis, Point3D screenCorner, Scene scene, 
								int yMin, int yMax, double horizontal, double vertical, int width, int height) {
			this.red = red;
			this.green = green;
			this.blue = blue;
			this.eye = eye;
			this.yAxis = yAxis;
			this.xAxis = xAxis;
			this.screenCorner = screenCorner;
			this.scene = scene;
			this.yMin = yMin;
			this.yMax = yMax;
			this.horizontal = horizontal;
			this.vertical = vertical;
			this.width = width;
			this.height = height;
		}
		
		@Override
		protected void compute() {
			if(yMax - yMin + 1 <= threshold) {
				computeDirect();
				return;
			}
			invokeAll(
					new CalculationJob(red, green, blue, eye, yAxis, xAxis, screenCorner, scene, yMin,
							yMin + (yMax - yMin)/2,horizontal, vertical, width, height),
					new CalculationJob(red, green, blue, eye, yAxis, xAxis, screenCorner, scene, yMin + (yMax - yMin)/2+1,
							yMax,horizontal, vertical, width, height)
				);
		}
		
		/**
		 * Method used to calculate color of a pixel called 
		 * when end condition of recursion is reached
		 */
		public void computeDirect() {
			short[] rgb = new short[3];
			int offset = yMin*width;
			for (int y = yMin; y <= yMax; y++) {
				for (int x = 0; x < width; x++) {
					Point3D screenPoint = screenCorner
							.add(xAxis.scalarMultiply(x / (width - 1.) * horizontal))
							.sub(yAxis.scalarMultiply(y / (height - 1.) * vertical));
					Ray ray = Ray.fromPoints(eye, screenPoint);

					Util.tracer(scene, ray, rgb);

					red[offset] = rgb[0] > 255 ? 255 : rgb[0];
					green[offset] = rgb[1] > 255 ? 255 : rgb[1];
					blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
					offset++;
					
				}
			}
		}
	}
	
	/**
	 * This method creates ray tracer that is able to take scene snapshots by
	 * using ray-tracing technique
	 * @return new ray tracer
	 */
	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {
			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp, double horizontal, double vertical,
					int width, int height, long requestNo, IRayTracerResultObserver observer) {
				System.out.println("Započinjem izračune...");
				short[] red = new short[width * height];
				short[] green = new short[width * height];
				short[] blue = new short[width * height];
				Point3D og = view.sub(eye).normalize();
				Point3D yAxis = viewUp.normalize().sub(og.scalarMultiply(og.scalarProduct(viewUp.normalize())))
						.normalize();
				Point3D xAxis = og.vectorProduct(yAxis).normalize();
//				Point3D zAxis = yAxis.vectorProduct(og).normalize();
				Point3D screenCorner = view.sub(xAxis.scalarMultiply(horizontal / 2))
						.add(yAxis.scalarMultiply(vertical / 2));
				Scene scene = RayTracerViewer.createPredefinedScene();

				ForkJoinPool pool = new ForkJoinPool();
				pool.invoke(new CalculationJob(red, green, blue, eye, yAxis, xAxis, screenCorner, scene,
												0 , height-1, horizontal, vertical, width, height));
				pool.shutdown();
				
				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
			}

		};

	}
}
