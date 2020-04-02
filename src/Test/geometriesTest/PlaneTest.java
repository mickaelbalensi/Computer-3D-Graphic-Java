package geometriesTest;

import geometries.Plane;
import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Util;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTest {
    @Test
    void getNormal(){
        Point3D pt1 =new Point3D(1,2,3);
        Point3D pt2=new Point3D(4,5,6);
        Point3D pt3=new Point3D(7,8,9);

        try{
            Plane plane=new Plane(pt1,pt2,pt3);
            Vector temp1=new Vector(pt1);
            Vector temp2=new Vector(pt2);
            Vector temp3=new Vector(pt3);
            Vector temp4=temp1.subtract(temp2);
            Vector temp5=temp1.subtract(temp3);
            Vector tempNormal=temp4.crossProduct(temp5).normalize();
            Vector nTest=plane.getNormal(pt1);
            assertTrue(Util.isZero( nTest.dotProduct(tempNormal)),"Wrong triangle getNormal");
        }catch(Exception e){}

    }

}