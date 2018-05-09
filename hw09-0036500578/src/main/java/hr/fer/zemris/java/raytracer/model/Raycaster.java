package hr.fer.zemris.java.raytracer.model;

import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

public class Raycaster {
	private static double DELTA = 1E-6;
	static int counter =0;
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(), new Point3D(10, 0, 0), new Point3D(0, 0, 0),
				new Point3D(0, 0, 10), 20, 20);
	}

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
				Point3D zAxis = yAxis.vectorProduct(og).normalize();
				Point3D screenCorner = view.sub(xAxis.scalarMultiply(horizontal / 2))
						.add(yAxis.scalarMultiply(vertical / 2));
				Scene scene = RayTracerViewer.createPredefinedScene();
				short[] rgb = new short[3];
				int offset = 0;
				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						Point3D screenPoint = screenCorner
								.add(xAxis.scalarMultiply(x / (width - 1.) * horizontal))
								.sub(yAxis.scalarMultiply(y / (height - 1.) * vertical));
						Ray ray = Ray.fromPoints(eye, screenPoint);

						tracer(scene, ray, rgb);

//						for(short s: rgb ) {
//							System.out.println(s);
//						}
						red[offset] = rgb[0] > 255 ? 255 : rgb[0];
						green[offset] = rgb[1] > 255 ? 255 : rgb[1];
						blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
						offset++;
						
					}
				}
				System.out.println(counter);
				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
			}

		};

	}
	
//	protected static void tracer(Scene scene, Ray ray, short[] rgb) {
//		rgb[0] = 0;
//		rgb[1] = 0;
//		rgb[2] = 0;
//		RayIntersection closest = findClosestIntersection(scene, ray);
//		if (closest == null) {
//			System.out.println("Nema intersectiona");
//			return;
//		}
//		rgb[0] = 255;
//		rgb[1] = 255;
//		rgb[2] = 255;
//	}

	private static void tracer(Scene scene, Ray ray, short[] rgb) {
		RayIntersection closestIntersection = findClosestIntersection(scene, ray);

		if (closestIntersection == null) {
			for (int i = 0; i < 3; ++i) {
				rgb[i] = 0;
			}
			return;
		}
		
		determineColorFor(scene, ray, closestIntersection, rgb);
	}

	private static RayIntersection findClosestIntersection(Scene scene, Ray ray) {
		RayIntersection closestIntersection = null;

		for (GraphicalObject object : scene.getObjects()) {
			RayIntersection current = object.findClosestRayIntersection(ray);
			
			if (closestIntersection == null && current != null) {
				closestIntersection = current;
				continue;
			}

			if (current != null && closestIntersection != null
					&& current.getDistance() < closestIntersection.getDistance()) {
				closestIntersection = current;
			}
		}
		return closestIntersection;
	}

	private static void determineColorFor(Scene scene, Ray ray, RayIntersection intersection, short[] rgb) {
		short[] ambient = { 15, 15, 15 };
		double[] diffuse = {0, 0, 0};
		double[] reflected = {0, 0, 0};
		
		for (LightSource ls : scene.getLights()) {
			Ray r = new Ray(ls.getPoint(), intersection.getPoint());
			RayIntersection lsIntersection = findClosestIntersection(scene, r);
			Point3D p = ls.getPoint().sub(intersection.getPoint());
			if (lsIntersection== null || (lsIntersection.getDistance()+DELTA < p.norm())) {
				continue;
			} else {

				Point3D l = ls.getPoint().sub(lsIntersection.getPoint()).normalize();
				double d0 = Math.abs(ls.getR() * lsIntersection.getKdr() * lsIntersection.getNormal().scalarProduct(l));
				double d1 = Math.abs(ls.getG() * lsIntersection.getKdg() * lsIntersection.getNormal().scalarProduct(l));
				double d2 = Math.abs(ls.getB() * lsIntersection.getKdb() * lsIntersection.getNormal().scalarProduct(l));
				
//				System.out.println(d0);
//				System.out.println(d1);
//				System.out.println(d2);
				Point3D r2 = intersection.getNormal().normalize()
						.scalarMultiply(2 * l.scalarProduct(lsIntersection.getNormal()) / lsIntersection.getNormal().norm()).sub(l)
						.normalize();
				Point3D v = ray.start.sub(intersection.getPoint()).normalize();
				
				double cos = Math.pow(r2.scalarProduct(v), lsIntersection.getKrn()) > 0 ? Math.pow(r2.scalarProduct(v), lsIntersection.getKrn()) : 0;
				reflected[0] += (short) ls.getR() * lsIntersection.getKrr() * cos;
				reflected[1] += (short) ls.getG() * lsIntersection.getKrg() * cos;
				reflected[2] += (short) ls.getB() * lsIntersection.getKrb() * cos;
				
				diffuse[0] += d0>0 ? d0:0;
				diffuse[1] += d1>0 ? d1:0;
				diffuse[2] += d2>0 ? d2:0;
			}
//			System.out.println(diffuse[0]);
//			System.out.println(diffuse[1]);
//			System.out.println(diffuse[2]);
//			System.out.println();
		}
//		System.out.println(diffuse[0]);
//		System.out.println(diffuse[1]);
//		System.out.println(diffuse[2]);
//		System.out.println();
		rgb[0] = (short) ((ambient[0] + diffuse[0] + reflected[0])); 
		rgb[1] = (short) ((ambient[1] + diffuse[1] + reflected[1]));
		rgb[2] = (short) ((ambient[2] + diffuse[2] + reflected[2])); 
//		System.out.println(color[0]);
//		System.out.println(color[1]);
//		System.out.println(color[2]);
	}
}
