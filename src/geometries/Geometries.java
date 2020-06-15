package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.*;

public class Geometries implements Intersectable {
    private List<Intersectable> shapes;

    public Geometries() {
        this.shapes = new ArrayList<Intersectable>();
    }

    public Geometries(Intersectable... geometries) {
        this.shapes = new ArrayList<>();
        for (int i = 0; i < geometries.length; i++) {
            this.shapes.add(geometries[i]);
        }
    }

    public void add(Intersectable... geometries) {
        for (int i = 0; i < geometries.length; i++) {
            this.shapes.add(geometries[i]);
        }
    }

    /***
     *
     * @param ray ray pointing toward a Geometry
     * @return all the intersections points between our rays and our shapes
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double max) {
        List<GeoPoint> intersectionPointsList = null;
        for (Intersectable geo : shapes) {
            List<GeoPoint> list = geo.findGeoIntersections(ray, max);
            if (list != null) {
                if (intersectionPointsList != null) intersectionPointsList = new LinkedList<>();
                intersectionPointsList.addAll(list);
            }
        }
        return intersectionPointsList;
    }
}
