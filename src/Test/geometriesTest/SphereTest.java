package geometriesTest;

import geometries.Sphere;
import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class SphereTest {


    @Test
    void getNormalTest() {
        Point3D center =new Point3D(1,2,3);
        Sphere sphere= new Sphere(center,5.0);
        Point3D point =new Point3D(6,2,3);

        try {
            Vector normalResult = sphere.getNormal(point);
            Vector expResult= new Vector(1,0,0);

            assertEquals(expResult,normalResult);
            assertTrue(expResult.length()==1);
        }catch(Exception e){}




    }


}