package geometriesTest;

import geometries.Sphere;
import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;
import geometries.*;

import static org.junit.jupiter.api.Assertions.*;

class TubeTest {
    @Test
    void getNormalTest() {
        Ray ray = new Ray(new Point3D(2, -3, 7), new Vector(3, -5, -2));
        Tube tube = new Tube(ray, 5.0);
        Point3D point = new Point3D(-1, -16, -3);

        try {
            Vector normalResult = tube.getNormal(point);
            Vector expResult = new Vector(-9, -3, -6).normalize();

            assertEquals(expResult, normalResult);
            assertTrue(Util.isZero( normalResult.dotProduct(ray.getDirection())));
            assertTrue(expResult.length() == 1);
        } catch (Exception e) {
        }
    }
}