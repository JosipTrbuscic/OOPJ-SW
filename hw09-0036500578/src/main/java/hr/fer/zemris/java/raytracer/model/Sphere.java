package hr.fer.zemris.java.raytracer.model;

/**
 * This class represents a sphere in three dimensional space.
 * Every sphere has its center represented by point in space 
 * and radius by positive real number. 
 * @author Josip Trbuscic
 *
 */
public class Sphere extends GraphicalObject {
	private Point3D center;
	private double radius;
	private double kdr;
	private double kdg;
	private double kdb;
	private double krr;
	private double krg;
	private double krb;
	private double krn;
	
	/**
	 * Constructor
	 * @param center - center of a sphere
	 * @param radius - radius of a sphere
	 * @param kdr - diffuse component of red color
	 * @param kdg - diffuse component of green color
	 * @param kdb - diffuse component of blue color
	 * @param krr - reflective component of red color
	 * @param krg - reflective component of green color
	 * @param krb - reflective component of blue color
	 * @param krn - intensity of reflective component
	 */
	public Sphere(Point3D center, double radius, double kdr, double kdg, double kdb, double krr, double krg, double krb,
			double krn) {
		if(radius < 0) throw new IllegalArgumentException("Radius of a sphere must be positive");
		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn  = krn;
	}

	@Override
	public RayIntersection findClosestRayIntersection(Ray ray) {
		Point3D v = ray.start.sub(center);
		double b = 2* ray.direction.scalarProduct(v);
		double c = v.scalarProduct(v) - radius*radius;
		double d = b*b-4*c;
		if(d<0) {
			return null;
		}
		double x1 = (-b - Math.sqrt(d)) / 2;
		double x2 = (-b + Math.sqrt(d)) / 2;
        if(x1 < 0 && x2 < 0) return null;
		double distance = x1 <  x2 ? x1 :x2;
		
		Point3D intersectionPoint = ray.start.add(ray.direction.scalarMultiply(distance));
		return new SphereRayIntersection(intersectionPoint, distance, intersectionPoint.sub(ray.start).norm() > radius);
	}
	
	/**
	 * Class that represents intersection of sphere and ray
	 * @author Josip Trbuscic
	 *
	 */
	private class SphereRayIntersection extends RayIntersection{

		protected SphereRayIntersection(Point3D point, double distance, boolean outer) {
			super(point, distance, outer);
			
		}

		@Override
		public Point3D getNormal() {
			return this.getPoint().sub(Sphere.this.center).modifyNormalize();
		}

		@Override
		public double getKdr() {
			return kdr;
		}

		@Override
		public double getKdg() {
			return kdg;
		}

		@Override
		public double getKdb() {
			return kdb;
		}

		@Override
		public double getKrr() {
			return krr;
		}

		@Override
		public double getKrg() {
			return krg;
		}

		@Override
		public double getKrb() {
			return krb;
		}

		@Override
		public double getKrn() {
			return krn;
		}
		
	}

	
}
