package hr.fer.zemris.java.raytracer.model;

import hr.fer.zemris.math.Vector3;

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
	
	public Sphere(Point3D center, double radius, double kdr, double kdg, double kdb, double krr, double krg, double krb,
			double krn) {
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

//	
//	@Override
//	public RayIntersection findClosestRayIntersection(Ray ray) {
//		Point3D p0 = ray.start;
//		Point3D v = ray.direction;
//		Point3D a = center.sub(p0);
//		
//		double root = getSquareRoot(v, a);
//		if(root < 0) return null;
//		
//		double first = (-2*(v.scalarProduct(a)+root))/(2*Math.pow(v.norm(), 2));
//		double second = (-2*(v.scalarProduct(a)-root))/(2*Math.pow(v.norm(), 2));
//		if(first < 0 && second<0) return null;
//		double distance = first<second ? first : second;
////		Point3D intersection = new Point3D(p0.x + v.x*distance, p0.y + v.y*distance, p0.z + v.z*distance);
//		Point3D intersection = ray.start.add(ray.direction.scalarMultiply(distance));
//		return new SphereRayIntersection(intersection, distance, intersection.sub(ray.start).norm() > radius);
//		
//	}
	
	private double getSquareRoot(Point3D v, Point3D a) {
		double first = 4*Math.pow((v.scalarProduct(a)),2);
		double second = -4*Math.pow(v.norm(),2)*(a.norm()-Math.pow(radius, 2));
//		if((first+second) <0) return -1;
		return Math.sqrt(first+second);
	}
	
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
