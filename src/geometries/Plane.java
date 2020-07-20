package geometries;

import primitives.*;

import java.util.ArrayList;
import java.util.List;
import static primitives.Util.isZero;
import static primitives.Util.alignZero;

/**
 * the geometries.Plane class is a shape plane extends geometries.Geometry
 */
public class Plane implements Geometry{

   protected Point3D _p;
   protected Vector _normal;//we need a point and a vector to make a plane
   protected double t;

   //region getters
   public Point3D getPt1() {
      return _p;
   }
   public Vector get_normal() {
      Vector V =new Vector(_p);
      Vector norm= _normal.crossProduct(V);
      return norm;
   }

   //endregion

   //region CTORs
   /**
    * Constructor for a Plane receiving 3 point to caracterise it, and it's color and the material
    *
    * @param pt1      (Point3D)
    * @param pt2      (Point3D)
    * @param pt3      (Point3D)
    */
   public Plane (Point3D pt1,Point3D pt2,Point3D pt3){
      Vector temp1=new Vector(pt1);
      Vector temp2=new Vector(pt2);
      Vector temp3=new Vector(pt3);

      Vector temp4=temp1.subtract(temp2);
      Vector temp5=temp1.subtract(temp3);

      Vector tempNormal=temp4.crossProduct(temp5);

      this._p=pt1;
      this._normal=tempNormal;
      t=-(new Vector(pt1).dotProduct(_normal));

   }

   public Plane (Vector vec, Point3D pt1){
      this._p=pt1;
      this._normal=vec;
   }

   @Override
   public List<Point3D> findIntersections(Ray ray) {

         if (_p.subtract(ray.getPt())==null) return null; // ray starts from point Q - no intersections
      if (isZero(_normal.dotProduct(ray.getDirection()))) // ray is parallel to the plane - no intersections
         return null;

      double t = alignZero(_normal.dotProduct(_p.subtract(ray.getPt())) / _normal.dotProduct(ray.getDirection()));

      if( t <= 0 )return  null ;
      else
      {
         return List.of(ray.getTargetPoint(t));
      }
   }

   //endregion

   @Override
   public  Vector getNormal(Point3D pt)  {
      return null;
   }


   @Override
   public String toString() {
      return "Plane{" +
              "_p=" + _p +
              ", _normal=" + _normal +
              '}';
   }
}
