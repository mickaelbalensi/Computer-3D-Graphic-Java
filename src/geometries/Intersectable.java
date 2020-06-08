package geometries;


import primitives.*;


import java.util.List;
import java.util.Objects;

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

        /**
         * Constructor who takes Geometry and Point3D
         * @param geo
         * @param pt
         */
        GeoPoint(Geometry geo,Point3D pt){
            this.geometry=geo;
            this.point=pt;
        }

        public Point3D getPoint(){
            return point;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof GeoPoint)) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return Objects.equals(geometry, geoPoint.geometry) &&
                    Objects.equals(point, geoPoint.point);
        }
    }

    /**
     * @param ray ray pointing toward a Geometry
     * @return List<Point3D> return values
     */
    //List<GeoPoint> findIntersections(Ray ray);
    default List<GeoPoint> findIntersections(Ray ray) {
        return findIntersections(ray, Double.POSITIVE_INFINITY);
    }
    List<GeoPoint> findIntersections(Ray ray, double max);

}
