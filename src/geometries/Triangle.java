package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.isZero;

/**
 * the geometries.Triangle class represents the geometry Triangle
 */
public class Triangle extends Polygon {



    /**
     * geometries.Triangle constructor receiving 3 points to form the Triangle
     * @param p1 the first Point3D
     * @param p2 the second Point3D
     * @param p3 the third Point3D
     * the three points who form the triangle
     */
    public Triangle(Point3D p1, Point3D p2, Point3D p3) {
        super(p1, p2, p3);
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        List<Point3D> intersections = _plane.findIntersections(ray);
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

        if  ((t1 > 0 && t2 > 0 && t3 > 0) || (t1 < 0 && t2 < 0 && t3 < 0)) return  intersections;
        else return null;

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

