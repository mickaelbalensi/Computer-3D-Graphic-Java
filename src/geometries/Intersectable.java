package geometries;


import primitives.*;


import java.util.List;

/**
 * Intersectable is a common interface for all geometries that are able
 * to intersect from a ray to their entity (Shape)
 */
public interface Intersectable {
    /**
     * the GeoPoint
     */
    public static class GeoPoint {
        public Geometry geometry;
        public Point3D point;
    }

    /**
     * @param ray ray pointing toward a Geometry
     * @return List<Point3D> return values
     */
    List<Point3D> findIntersections(Ray ray);
}
