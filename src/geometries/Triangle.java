package geometries;

import primitives.*;

import java.util.List;

import static primitives.Util.isZero;

public class Triangle extends Polygon {

    //region CTORs

    /**
     * Ctor receiving 3 points with color and material
     * @param p1 first point
     * @param p2 second point
     * @param p3 third point
     * @param emissionColor emission color
     * @param material material
     */
    public Triangle(Point3D p1, Point3D p2, Point3D p3, Color emissionColor, Material material) {
        super(emissionColor, material, p1, p2, p3);
    }

    /**
     * triangle ctor
     * @param emissionColor emission color
     * @param material material
     * @param p1 p1
     * @param p2 p2
     * @param p3 p3
     */
    public Triangle(Color emissionColor, Material material, Point3D p1, Point3D p2, Point3D p3) {
        this(p1, p2, p3, emissionColor, material);
    }

    /**
     * the same ctor without material
     * @param p1 p1
     * @param p2 p2
     * @param p3 p3
     * @param emissionColor emission color
     */
    public Triangle(Point3D p1, Point3D p2, Point3D p3, Color emissionColor) {
        super(emissionColor, p1, p2, p3);
    }

    /**
     * the same ctor without color and material
     * @param p1 p1
     * @param p2 p2
     * @param p3 p3
     */
    public Triangle(Point3D p1, Point3D p2, Point3D p3) {
        super(p1, p2, p3);
    }
    //endregion

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double max) {
        List<GeoPoint> intersections = _plane.findGeoIntersections(ray, max);
        if (intersections == null) return null;

        Point3D pt = ray.getPt();
        Vector v = ray.getDirection();
        Vector v1 = _vertices.get(0).subtract(pt);
        Vector v2 = _vertices.get(1).subtract(pt);
        Vector v3 = _vertices.get(2).subtract(pt);

        double t1 = v.dotProduct(v1.crossProduct(v2));
        if (isZero(t1)) return null;
        double t2 = v.dotProduct(v2.crossProduct(v3));
        if (isZero(t2)) return null;
        double t3 = v.dotProduct(v3.crossProduct(v1));
        if (isZero(t3)) return null;

        if ((t1 > 0 && t2 > 0 && t3 > 0) || (t1 < 0 && t2 < 0 && t3 < 0)) {
            //for GeoPoint
            intersections.get(0).geometry = this;
            return intersections;
            //return intersections;
        }

        // the intersection point with the plane is outside the triangle
        return null;
    }

    @Override
    public String toString() {
        String result = "";
        for (Point3D p : _vertices) {
            result += p.toString();
        }
        return result;
    }
}


