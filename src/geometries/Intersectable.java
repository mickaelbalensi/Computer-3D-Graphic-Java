package geometries;


import primitives.*;


import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        GeoPoint(Geometry geo, Point3D pt) {
            this.geometry = geo;
            this.point = pt;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof GeoPoint)) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return geometry == geoPoint.geometry && point.equals(geoPoint.point);
        }
    }


    /**
     *  This function calculates all of intersect points from a ray (receiving in parameter)
     *to his entity (Shape) and return List of all intersect points
     *@param ray ray pointing toward a Geometry
     * @return l2
     */
    default List<Point3D> findIntersections(Ray ray) {
        List<GeoPoint> l1 = findGeoIntersections(ray);
        if (l1 == null) return null;
        List<Point3D> l2 = l1.stream().map(gp -> gp.point).collect(Collectors.toList());
        return l2;
    }

    default List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
    }

    List<GeoPoint> findGeoIntersections(Ray ray, double max);

    double getXmin();
    double getYmin();
    double getZmin();
    double getXmax();
    double getYmax();
    double getZmax();
}
