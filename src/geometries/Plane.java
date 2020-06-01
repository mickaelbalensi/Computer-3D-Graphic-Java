package geometries;

//import elements.Material;
import primitives.Point3D;
import primitives.*;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class Plane extends Geometry {

    Point3D _p; //Q
    Vector _normal;

    public Plane(Color emissionLight, Material material, Point3D p1, Point3D p2, Point3D p3) {
        super(emissionLight, material);

        _p = new Point3D(p1);

        Vector U = new Vector(p1, p2);
        Vector V = new Vector(p1, p3);
        Vector N = U.crossProduct(V);
        N.normalize();

        _normal = N;
//        _normal = N.scale(-1);

    }

    public Plane(Color emissionLight, Point3D p1, Point3D p2, Point3D p3) {
        this(emissionLight, new Material(0, 0, 0), p1, p2, p3);
    }

    public Plane(Point3D p1, Point3D p2, Point3D p3) {
        this(Color.BLACK, p1, p2, p3);
    }

    public Plane(Point3D _p, Vector _normal) {
        super(Color.BLACK, new Material(0, 0, 0));

        this._p = new Point3D(_p);
        this._normal = new Vector(_normal);
    }

    @Override
    public Vector getNormal(Point3D p) {
        return _normal;
    }

    //because polygon needs geNormal without parameter
    public Vector getNormal() {
        return getNormal(null);
    }

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

        if (t <= 0) {
            return null;
        }

        GeoPoint geo = new GeoPoint(this, ray.getTargetPoint(t));
        return List.of(geo);
    }

}

/*
package geometries;

import primitives.*;

import java.util.ArrayList;
import java.util.List;

import static primitives.Util.isZero;
import static primitives.Util.alignZero;


*/
/**
 * the geometries.Plane class is a shape plane extends geometries.Geometry
 *//*

public class Plane extends Geometry {

    protected Point3D _p;
    protected Vector _normal;//we need a point and a vector to make a plane
    protected double t;


    //region CTORs

    */
/**
     * Constructor for a Plane receiving 3 point to caracterise it, and it's color and the material
     *
     * @param pt1      (Point3D)
     * @param pt2      (Point3D)
     * @param pt3      (Point3D)
     * @param color    (Color)
     * @param material (Material)
     *//*

    public Plane(Point3D pt1, Point3D pt2, Point3D pt3, Color color, Material material) {
        super(color, material);
        Vector temp1 = new Vector(pt1);
        Vector temp2 = new Vector(pt2);
        Vector temp3 = new Vector(pt3);

        Vector temp4 = temp1.subtract(temp2);
        Vector temp5 = temp1.subtract(temp3);

        Vector tempNormal = temp4.crossProduct(temp5);

        this._p = pt1;
        this._normal = tempNormal;
        t = -(new Vector(pt1).dotProduct(_normal));

    }

    */
/**
     * Constructor for a Plane receiving 3 point to caracterise it, and it's color
     *
     * @param pt1
     * @param pt2
     * @param pt3
     * @param color
     *//*

    public Plane(Point3D pt1, Point3D pt2, Point3D pt3, Color color) {
        this(pt1, pt2, pt3, color, new Material(0, 0, 0));
    }

    */
/**
     * Constructor for a Plane receiving 3 point to caracterise it
     *
     * @param pt1
     * @param pt2
     * @param pt3
     *//*

    public Plane(Point3D pt1, Point3D pt2, Point3D pt3) {
        this(pt1, pt2, pt3, Color.BLACK, new Material(0, 0, 0));
    }

    */
/**
     * Ctor receiving:
     * @param vec
     * @param pt1
     * @param color
     * @param material
     *//*

    public Plane(Vector vec, Point3D pt1,Color color,Material material) {
        super(color,material);

        this._p = pt1;
        this._normal = vec;
    }

    */
/**
     * Ctor receiving
     * @param vec
     * @param pt1
     * @param color
     *//*

    public Plane(Vector vec, Point3D pt1,Color color) {
        super(color);

        this._p = pt1;
        this._normal = vec;
    }
    */
/**
     * @param vec
     * @param pt1 the vector and the point who form the plane
     *//*

    public Plane(Vector vec, Point3D pt1) {
        super();

        this._p = pt1;
        this._normal = vec;
    }

    */
/**
     * @param ray ray pointing toward a Geometry
     * @return Point3D if there is an intersection between the ray and the plane
     *//*

    @Override
    public List<GeoPoint> findIntersections(Ray ray) {

        ArrayList<GeoPoint> intersections = new ArrayList<>();
        if (_p.subtract(ray.getPt()) == null) return null; // ray starts from point Q - no intersections
        if (isZero(_normal.dotProduct(ray.getDirection()))) // ray is parallel to the plane - no intersections
            return null;

        double t = alignZero(_normal.dotProduct(_p.subtract(ray.getPt())) / _normal.dotProduct(ray.getDirection()));

        if (t <= 0) return null;
        else {
            GeoPoint intersection = new GeoPoint(this, ray.getPt().add(ray.getDirection().scale(t)));
            intersections.add(intersection);
            return intersections;
        }
    }






*/
/*
   public Plane1 (Vector vec, Point3D pt1){
      // ax+by+cz+d=0
      double a =vec.getPt().getX().get();
      double x =pt1.getX().get();
      double b =vec.getPt().getY().get();
      double y=pt1.getY().get();
      double c=vec.getPt().getZ().get();
      double z= pt1.getZ().get();

      double d=(a*x+b*y+c*z)*-1;

      double x1=0,x2=1,y1=1,y2=0,z1,z2;

      if(vec.getPt().getZ().get()==0) {
         z1 = 0;
         z2 = 0;
      }else {
        z1=(-d-a*x1-b*y1)/c;
        z2=(-d-a*x2-b*y2)/c;
      }

      Point3D pt2=new Point3D(x1,y1,z1);
      Point3D pt3=new Point3D(x2,y2,z2);

      this._p=pt1;
      this.pt2=pt2;
      this.pt3=pt3;

   }
*//*

    //endregion
    //region getters

    */
/**
     * getter of one Point in plane
     *
     * @return Point3D
     *//*

    public Point3D getPt1() {
        return _p;
    }

    @Override
    public Vector getNormal(Point3D pt) {
        return _normal;
        */
/*Vector V = new Vector(_p);
        Vector norm = _normal.crossProduct(V);
        return norm;*//*

    }
    //endregion

    @Override
    public String toString() {
        return "Plane{" +
                "_p=" + _p +
                ", _normal=" + _normal +
                '}';
    }
}
*/
