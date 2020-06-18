package geometries;

import primitives.*;

import java.util.ArrayList;
import java.util.List;

import static primitives.Util.alignZero;

/**
 * the geometries.Sphere class represents geometry sphere
 * this class extends geometries.RadialGeometry for his radius
 */
public class Sphere extends RadialGeometry {
    private Point3D center;

    //region CTORs

    /**
     * Constructor for a sphere receiving :
     * @param center        it's center (Point3D)
     * @param radius        it's radius (double)
     * @param emissionColor it's color (Color)
     * @param material      it's material
     * therefore we can construct a full sphere thanks to his center and radius and the color and his material
     */
    public Sphere(Point3D center, double radius, Color emissionColor, Material material) {
        super(radius, emissionColor, material);
        this.center = center;
    }

    public Sphere(Color emissionColor, Material material, double radius, Point3D center) {
        this(center, radius, emissionColor, material);
    }

    /**
     * Constructor who takes Point3D doubles and  Color
     *
     * @param center        (Point3D)
     * @param radius        (double)
     * @param emissionColor (Color)
     */
    public Sphere(Point3D center, double radius, Color emissionColor) {
        this(center, radius, emissionColor, Material.DEFAULT);
    }

    /**
     * the geometries.Sphere constructor receiving the middle Point of the sphere and his radius
     *
     * @param center the center Point3D
     * @param radius by type double
     */
    public Sphere(Point3D center, double radius) {
        this(center, radius, Color.BLACK);
    }
    //endregion

    /**
     * the getter of the center of the Sphere
     * @return center by type Point3D
     */
    public Point3D getCenter() {
        return center;
    }

    @Override
    public Vector getNormal(Point3D pt) {
        return pt.subtract(this.center).normalize();
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "_center=" + center +
                ", _radius=" + radius +
                '}';
    }

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double max) {
        Point3D p0 = ray.getPt();

        if (p0.equals(this.center)) //if the ray starts in the middle
            return List.of(new GeoPoint(this, ray.getTargetPoint(radius)));

        Vector u = this.center.subtract(p0);
        // tm - offset of projection of the center onto the ray
        double tm = alignZero(u.dotProduct(ray.getDirection()));

        // squared distance from the center to the ray
        double dSqrd = alignZero(u.lengthSquared() - tm * tm);
        // if the distance is equal or greater than the radius - no intersections
        if (alignZero(dSqrd - radiusSqrd) >= 0) return null;

        // distance from the center projection onto the ray to the intersection points
        double th = alignZero(Math.sqrt(radiusSqrd - dSqrd)); // th can only be non-negative
        if (th == 0) return null; // tangent ray's line
        double t1 = alignZero(tm + th); // it will be the bigger intersection offset value
        if (t1 <= 0) return null; // if the bigger offset intersection is before or at the ray point - no intersections

        double t2 = alignZero(tm - th); // it will be the smaller intersection offset value
        if (alignZero(t2 - max) >= 0) return null; // if the smaller one is beyond the max - no intersections

        if (alignZero(t1 - max) >= 0) { // the bigger one is beyond the max
            if (t2 <= 0) return null; // if the smaller one is at or before the ray point - no intersections
            return List.of(new GeoPoint(this, ray.getTargetPoint(t2))); // t2 is the only intersection
        }

        // t1 is intersection - check if it's the only one
        // if the smaller one is at or before the ray point - t1 is the only intersection
        if (t2 <= 0) return List.of(new GeoPoint(this, ray.getTargetPoint(t1)));

        // both t1 and t2 are intersections
        return List.of(new GeoPoint(this, ray.getTargetPoint(t1)), //
                       new GeoPoint(this, ray.getTargetPoint(t2)));
    }
}
