package geometries;

import primitives.*;
public class Plane implements Geometry{

   protected Point3D _p;
   protected Vector _normal;//we need a point and a vector to make a plane

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
   public Plane (Point3D pt1,Point3D pt2,Point3D pt3){
      Vector temp1=new Vector(pt1);
      Vector temp2=new Vector(pt2);
      Vector temp3=new Vector(pt3);

      Vector temp4=temp1.subtract(temp2);
      Vector temp5=temp1.subtract(temp3);

      Vector tempNormal=temp4.crossProduct(temp5);

      this._p=pt1;
      this._normal=tempNormal;

   }

   public Plane (Vector vec, Point3D pt1){
      this._p=pt1;
      this._normal=vec;
   }



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
*/
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
