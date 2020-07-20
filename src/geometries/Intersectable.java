package geometries;


import primitives.*;


import java.util.List;

/**
 * Intersectable is a common interface for all geometries that are able
 * to intersect from a ray to their entity (Shape)
 */
public interface Intersectable {
    /**
     * ray ray pointing toward a Geometry
     * @param ray ray
     * @return 3D
     */
    List<Point3D> findIntersections(Ray ray);
}
