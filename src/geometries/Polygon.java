package geometries;

import primitives.Color;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import primitives.*;

import static primitives.Util.*;

/**
 * geometries.Polygon class represents two-dimensional polygon in 3D Cartesian coordinate
 * system
 *
 * @author Dan
 */
public class Polygon extends Geometry implements Volume{
    /**
     * List of polygon's vertices
     */
    protected List<Point3D> _vertices;

    /**
     * Associated plane in which the polygon lays
     */
    protected Plane _plane;

    //region CTORs

    /**
     * geometries.Polygon constructor based on vertices list. The list must be ordered by edge
     * path. The polygon must be convex.
     *
     * @param color    the color of the polygon
     * @param material it's material
     * @param vertices list of vertices according to their order by edge path
     * @throws IllegalArgumentException in any case of illegal combination of vertices:
     *                                  <ul>
     *                                  <li>Less than 3 vertices</li>
     *                                  <li>Consequent vertices are in the same point
     *                                  <li>The vertices are not in the same plane</li>
     *                                  <li>The order of vertices is not according to edge path</li>
     *                                  <li>Three consequent vertices lay in the same line (180&#176; angle between two
     *                                  consequent edges)
     *                                  <li>The polygon is concave (not convex)</li>
     *                                  </ul>
     */
    public Polygon(Color color, Material material, Point3D... vertices) {
        super(color, material);
        if (vertices.length < 3)
            throw new IllegalArgumentException("A polygon can't have less than 3 vertices");
        _vertices = List.of(vertices);
        // Generate the plane according to the first three vertices and associate the
        // polygon with this plane.
        // The plane holds the invariant normal (orthogonal unit) vector to the polygon
        _plane = new Plane(vertices[0], vertices[1], vertices[2]);

        //calculate the minimum and maximum of coordinates X,Y,Z of the polygon
        Xmin = MAX;
        Ymin = MAX;
        Zmin = MAX;
        Xmax = MIN;
        Ymax = MIN;
        Zmax = MIN;

        for (Point3D p : _vertices) {
            double xPoint = p.getX().get();
            double yPoint = p.getY().get();
            double zPoint = p.getZ().get();

            if (Xmin > xPoint)  Xmin = xPoint;
            if (Ymin > yPoint)  Ymin = yPoint;
            if (Zmin > zPoint)  Zmin = zPoint;

            if (Xmax < xPoint)  Xmax = xPoint;
            if (Ymax < yPoint)  Ymax = yPoint;
            if (Zmax < zPoint)  Zmax = zPoint;
        }

        if (vertices.length == 3) return; // no need for more tests for a Triangle

        Vector n = _plane.getNormal(vertices[0]);

        // Subtracting any subsequent points will throw an IllegalArgumentException
        // because of Zero Vector if they are in the same point
        Vector edge1 = vertices[0].subtract(vertices[vertices.length - 1]);
        Vector edge2 = vertices[1].subtract(vertices[0]);

        // Cross Product of any subsequent edges will throw an IllegalArgumentException
        // because of Zero Vector if they connect three vertices that lay in the same
        // line.
        // Generate the direction of the polygon according to the angle between last and
        // first edge being less than 180 deg. It is hold by the sign of its dot product
        // with
        // the normal. If all the rest consequent edges will generate the same sign -
        // the
        // polygon is convex ("kamur" in Hebrew).
        boolean positive = edge1.crossProduct(edge2).dotProduct(n) > 0;
        for (int i = 2; i < vertices.length; ++i) {
            // Test that the point is in the same plane as calculated originally
            if (!isZero(vertices[i].subtract(vertices[0]).dotProduct(n)))
                throw new IllegalArgumentException("All vertices of a polygon must lay in the same plane");
            // Test the consequent edges have
            edge1 = edge2;
            edge2 = vertices[i].subtract(vertices[i - 1]);
            if (positive != (edge1.crossProduct(edge2).dotProduct(n) > 0))
                throw new IllegalArgumentException("All vertices must be ordered and the polygon must be convex");
        }

    }

    /**
     * Same Constructor without material
     *
     * @param color polygon's color
     * @param vertices list of polygon's points
     */
    public Polygon(Color color, Point3D... vertices) {
        this(color, new Material(0, 0, 0), vertices);
    }

    /**
     * Same Constructor without color and material
     *
     * @param vertices list of polygon's points
     */
    public Polygon(Point3D... vertices) {
        this(Color.BLACK, vertices);
    }
    //endregion

    @Override
    public Vector getNormal(Point3D point) {
        return _plane.getNormal(point);
    }

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double max) {
        List<GeoPoint> intersections = _plane.findGeoIntersections(ray);
        if (intersections == null) return null;

        Point3D p0 = ray.getPt();
        Vector v = ray.getDirection();

        Vector v1 = _vertices.get(1).subtract(p0);
        Vector v2 = _vertices.get(0).subtract(p0);
        double sign = v.dotProduct(v1.crossProduct(v2));
        if (isZero(sign))
            return null;

        boolean positive = sign > 0;

        for (int i = _vertices.size() - 1; i > 0; --i) {
            v1 = v2;
            v2 = _vertices.get(i).subtract(p0);
            sign = alignZero(v.dotProduct(v1.crossProduct(v2)));
            if (isZero(sign)) return null;
            if (positive != (sign > 0)) return null;
        }
        intersections.get(0).geometry = this;
        return intersections;
    }

    public List<Point3D> getVertices() {
        return _vertices;
    }

}