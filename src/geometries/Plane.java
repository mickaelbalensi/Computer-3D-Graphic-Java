package geometries;

import primitives.*;

import java.util.ArrayList;
import java.util.List;

import static primitives.Util.isZero;
import static primitives.Util.alignZero;


/**
 * the geometries.Plane class is a shape plane extends geometries.Geometry
 */
public class Plane extends Geometry {

    protected Point3D _p;
    protected Vector _normal;//we need a point and a vector to make a plane



    //region CTORs

    /**
     * Constructor for a Plane receiving 3 point to caracterise it, and it's color and the material
     *
     * @param pt1      (Point3D)
     * @param pt2      (Point3D)
     * @param pt3      (Point3D)
     * @param color    (Color)
     * @param material (Material)
     */
    public Plane(Point3D pt1, Point3D pt2, Point3D pt3, Color color, Material material) {
        super(color, material);

        Vector temp1 = new Vector(pt1);
        Vector temp2 = new Vector(pt2);
        Vector temp3 = new Vector(pt3);

        Vector temp4 = temp1.subtract(temp2);
        Vector temp5 = temp1.subtract(temp3);

        Vector tempNormal = temp4.crossProduct(temp5).normalized();

        this._p = new Point3D(pt1);

        this._normal = tempNormal;

    }

    /**
     * Constructor for a Plane receiving 3 point to caracterise it, and it's color
     *
     * @param pt1
     * @param pt2
     * @param pt3
     * @param color
     */
    public Plane(Point3D pt1, Point3D pt2, Point3D pt3, Color color) {
        this(pt1, pt2, pt3, color, new Material(0, 0, 0));
    }

    /**
     * Constructor for a Plane receiving 3 point to caracterise it
     *
     * @param pt1
     * @param pt2
     * @param pt3
     */
    public Plane(Point3D pt1, Point3D pt2, Point3D pt3) {
        this(pt1, pt2, pt3, Color.BLACK, new Material(0, 0, 0));
    }

    /**
     * Ctor receiving:
     * @param vec
     * @param pt1
     * @param color
     * @param material
     */
    public Plane(Vector vec, Point3D pt1,Color color,Material material) {
        super(color,material);

        this._p = pt1;
        this._normal = vec;
    }

    /**
     * Ctor receiving
     * @param vec
     * @param pt1
     * @param color
     */
    public Plane(Vector vec, Point3D pt1,Color color) {
        super(color);

        this._p = pt1;
        this._normal = vec;
    }
    /**
     * @param vec
     * @param pt1 the vector and the point who form the plane
     */
    public Plane(Vector vec, Point3D pt1) {
        super();

        this._p = pt1;
        this._normal = vec;
    }

    /**
     * @param ray ray pointing toward a Geometry
     * @return Point3D if there is an intersection between the ray and the plane
     */
    @Override
    public List<GeoPoint> findIntersections(Ray ray) {
        Vector p0Q;
        try {
            p0Q = _p.subtract(ray.getPt());
        } catch (IllegalArgumentException e) {
            return null; // ray starts from point Q - no intersections
        }

        double nv = _normal.dotProduct(ray.getDirection());
        if (isZero(nv)) // ray is parallel to the plane - no intersections
            return null;

        double t = alignZero(_normal.dotProduct(p0Q) / nv);

        return t <= 0 ? null : List.of( new GeoPoint(this, ray.getTargetPoint(t)));
    }


    //endregion
    //region getters

    /**
     * getter of one Point in plane
     *
     * @return Point3D
     */
    public Point3D getPoint() {
        return _p;
    }
    @Override
    public Vector getNormal(Point3D pt) {
        //Vector V = new Vector(_p);
        //Vector norm = _normal.crossProduct(V);
        //return norm;
        return _normal;
    }
    //endregion

    public Vector getNormal() {
        return getNormal(null);
    }


    @Override
    public String toString() {
        return "Plane{" +
                "_p=" + _p +
                ", _normal=" + _normal +
                '}';
    }
}