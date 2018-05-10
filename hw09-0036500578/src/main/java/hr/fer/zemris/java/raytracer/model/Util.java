package hr.fer.zemris.java.raytracer.model;

/**
 * Utility class used by ray casters. This class offers
 * method for tracing rays starting from observer and 
 * ending on a object in space and methods for calculating 
 * color of a pixel.
 * @author Josip Trbuscic
 *
 */
public class Util {
	
	/**
	 * Tolerance of intersection ray length difference
	 */
	private static final double DELTA = 1E-6;
	
	/**
	 * Lightning ambient component
	 */
	private static final int[] AMBIENT_COMPONENT = {15, 15, 15};
	
	/**
	 * Method which determines if given ray intersects with any object in the scene 
	 * and calculates its color
	 * @param scene - scene
	 * @param ray - light ray
	 * @param rgb - color
	 */
	public static void tracer(Scene scene, Ray ray, short[] rgb) {
		RayIntersection closestIntersection = findClosestIntersection(scene, ray);
		if (closestIntersection == null) {
			for (int i = 0; i < 3; i++) {
				rgb[i] = 0;
			}
			return;
		} 
		determineColorFor(closestIntersection, ray, scene, rgb);
	}
	
	/**
	 * Searches for the closest intersection of a given ray and 
	 * any object in the scene
	 * @param scene - scene
	 * @param ray - light ray
	 * @return closest ray-object intersection if such can be found, null otherwise 
	 */
	private static RayIntersection findClosestIntersection(Scene scene, Ray ray) {
		RayIntersection closestIntersection = null;

		for (GraphicalObject object : scene.getObjects()) {
			RayIntersection current = object.findClosestRayIntersection(ray);
			
			if (closestIntersection == null && current != null) {
				closestIntersection = current;
				continue;
			}

			if (current != null && closestIntersection != null
					&& current.getDistance() + DELTA< closestIntersection.getDistance()) {
				closestIntersection = current;
			}
		}
		return closestIntersection;
	}
	
	/**
	 * Determines color for the given intersection of the given ray 
	 * @param intersection - ray-object intersection
	 * @param ray - light ray
	 * @param scene - scene
	 * @param rgb - color
	 */
	private static void determineColorFor(RayIntersection intersection, Ray ray, Scene scene, short[] rgb) {
		double[] diffuse = {0, 0, 0};
		double[] reflected = {0, 0, 0};
		for (LightSource ls : scene.getLights()) {
			Ray r = Ray.fromPoints(ls.getPoint(), intersection.getPoint());
			RayIntersection lsIntersection = findClosestIntersection(scene, r);
			
			Point3D p = ls.getPoint().sub(intersection.getPoint());
			if(lsIntersection == null || (lsIntersection.getDistance()+DELTA < p.norm())) {
				continue;
			}
			
			modifyDiffuseComponent(ls, lsIntersection, diffuse);
			modifyReflectiveComponent(ls, lsIntersection, ray.start, reflected);
		}
		
		for (int i = 0; i < 3; i++) {
			rgb[i] = (short) (AMBIENT_COMPONENT[i] + diffuse[i]+reflected[i]);
		}
		
	}
	
	/**
	 * Modifies given color array by adding diffuse light component to it 
	 * @param ls - light source
	 * @param lsIntersection - intersection of the ray coming from light source and the object
	 * @param diffuse - diffuse component 
	 */
	private static void modifyDiffuseComponent(LightSource ls, RayIntersection lsIntersection, double[] diffuse) {
		
		Point3D l = ls.getPoint().sub(lsIntersection.getPoint()).normalize();
		double ln = l.scalarProduct(lsIntersection.getNormal());
		if(ln < 0 ) return;
		
		diffuse[0] += ls.getR() * lsIntersection.getKdr() * ln;
		diffuse[1] += ls.getG() * lsIntersection.getKdg() * ln;
		diffuse[2] += ls.getB() * lsIntersection.getKdb() * ln;
		
	}
	
	/**
	 * Modifies given color array by adding reflected light component to it 
	 * @param ls - light source
	 * @param lsIntersection - intersection of the ray coming from light source and the object
	 * @param eye - observer
	 * @param reflected - diffuse component 
	 */
	private static void modifyReflectiveComponent(LightSource ls, RayIntersection lsIntersection, Point3D eye, double[] reflected) {
		Point3D l = lsIntersection.getPoint().sub(ls.getPoint()).normalize();
		Point3D n = lsIntersection.getNormal();
		
		Point3D r = l.sub(n.scalarMultiply(2 * l.scalarProduct(n)) ).normalize();
		Point3D v = eye.sub(lsIntersection.getPoint()).normalize();
		
		double rv = r.scalarProduct(v);
		if(rv < 0)	return;
		reflected[0] += ls.getR() * lsIntersection.getKrr() * Math.pow(rv, lsIntersection.getKrn());
		reflected[1] += ls.getG() * lsIntersection.getKrg() * Math.pow(rv, lsIntersection.getKrn());
		reflected[2] += ls.getB() * lsIntersection.getKrb() * Math.pow(rv, lsIntersection.getKrn());
	}
}
