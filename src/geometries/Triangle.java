package geometries;

import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import geometries.Intersectable;

import java.awt.*;
import java.util.List;
import java.util.Objects;

import static primitives.Util.isZero;

// <<<<<<<< itshak
public class Triangle extends Plane {
    private Point3D p1;
    private Point3D p2;
    private Point3D p3;



    /**
     *
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Triangle)) return false;
        Triangle triangle = (Triangle) o;
        return Objects.equals(p1, triangle.p1) &&
                Objects.equals(p2, triangle.p2) &&
                Objects.equals(p3, triangle.p3);
    }

    @Override
    public int hashCode() {
        return Objects.hash(p1, p2, p3);
    }

//=======
/**
 * the geometries.Triangle class represents the geometry Triangle
 *//*
public class Triangle extends Polygon {


//>>>>>>> master


    /**
     * geometries.Triangle constructor receiving 3 points to form the Triangle
     * @param p1 the first Point3D
     * @param p2 the second Point3D
     * @param p3 the third Point3D
     * the three points who form the triangle
     */
    public Triangle(Point3D p1, Point3D p2, Point3D p3, Color color) {
        super(p1, p2, p3,color);
        this.p1=p1;
        this.p2=p2;
        this.p3=p3;
    }

    /**
     *
     * @param ray ray pointing toward a Geometry
     * @return Point3D if there is an intersection between the point and the triangle
     */
    @Override
    public List<GeoPoint> findIntersections(Ray ray) {
        List<GeoPoint> intersections = super.findIntersections(ray);
        if (intersections == null) return null;

        Point3D pt = ray.getPt();
        Vector v = ray.getDirection();
        Vector v1 = p1.subtract(pt);
        Vector v2 = p2.subtract(pt);
        Vector v3 = p3.subtract(pt);

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

