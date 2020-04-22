package geometriesTest;

import geometries.Intersectable;
import geometries.Plane;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class TriangleTest {
    @Test
    void getNormal() {
        Point3D ptTest = new Point3D(10, 11, 12);
        Point3D pt1 = new Point3D(1, 2, 3);
        Point3D pt2 = new Point3D(4, 5, 6);
        Point3D pt3 = new Point3D(7, 8, 9);
        try{
            Plane plane = new Plane(pt1, pt2, pt3);
            Vector temp1 = new Vector(pt1);
            Vector temp2 = new Vector(pt2);
            Vector temp3 = new Vector(pt3);

            Vector temp4 = temp1.subtract(temp2);
            Vector temp5 = temp1.subtract(temp3);
            Vector tempNormal = temp4.crossProduct(temp5).normalize();
            Vector nTest = plane.getNormal(ptTest);
            assertTrue(Util.isZero(nTest.dotProduct(tempNormal)), "Wrong triangle getNormal");
        }catch(Exception e){}
    }

    @Test
    public void testFindIntersections() {
        Triangle t1 = new Triangle(new Point3D(1.0d, 0.0d, 0.0d),
                new Point3D(0.0d, 1.0d, 0.0d),
                new Point3D(0.0d, 0.0d, 1.0d));
        System.out.println("Test 1 : the Ray begin at the vertex of the Triangle");
        Ray r1 = new Ray(new Point3D(1.0d, 0.0d, 0.0d), new Vector(2.0d, 0.0d, 0.0d));

        Point3D p1 =  t1.findIntersections(r1).get(0);
        assertNull( p1,"wrong Test 1 ");

        System.out.println("Test 2 : the Ray begin before the vertex of the Triangle");
        Ray r2 = new Ray(new Point3D(0.0d, 0.0d, 0.0d), new Vector(2.0d, 0.0d, 0.0d));

        Point3D p2 = t1.findIntersections(r2).get(0);
        assertEquals(new Point3D(1.0d, 0.0d, 0.0d), p2,"wrong Test 2");

        System.out.println("Test 3 : the Ray begin at the edge of the Triangle");
        Ray r3 = new Ray(new Point3D(0.5d, 0.0d, 0.5d), new Vector(0.5d, 0.0d, 0.5d));
        Point3D p3 = t1.findIntersections(r3).get(0);

        assertEquals(new Point3D(0.5d, 0.0d, 0.5d), p3,"wrong Test 3 ");

        System.out.println("Test 4 : the Ray begin before the edge of the Triangle");
        Ray r4 = new Ray(new Point3D(0.0d, 0.0d, 0.0d), new Vector(1.0d, 0.0d, 1.0d));
        Point3D p4 = t1.findIntersections(r4).get(0);

        assertEquals(new Point3D(0.5d, 0.0d, 0.5d), p4,"wrong Test 4 ");

        System.out.println("Test 5 : the Ray begin at the extension of the edge of the Triangle");
        Ray r5 = new Ray(new Point3D(0.0d, -1.0d, 2.0d), new Vector(-2.0d, -1.0d, 0.0d));
        assertNull(t1.findIntersections(r5),"wrong Test 5 ");

        System.out.println("Test 6 : the Ray begin before the extension of the edge of the Triangle");
        Ray r6 = new Ray(new Point3D(1.0d, -0.5d, 2.0d), new Vector(-3.0d, -1.5d, 0.0d));
        assertNull(t1.findIntersections(r6),"wrong Test 6");

        System.out.println("Test 7 : the Ray begin at the plane anywhere");
        Ray r7 = new Ray(new Point3D(-1.0d, 1.5d, 0.5d), new Vector(0.0d, -1.5d, -0.5d));
        assertNull(t1.findIntersections(r7),"wrong Test 7 ");

        System.out.println("Test 8 : the Ray begin on the Triangle");
        Ray r8 = new Ray(new Point3D(-0.15d, -0.18d, 1.34d), new Vector(0.15d, -0.82d, -1.34d));
        assertNull(t1.findIntersections(r8),"wrong Test 8 ");

        Triangle t2 = new Triangle(new Point3D(3.0d, 0.0d, 0.0d),
                new Point3D(0.0d, 3.0d, 0.0d),
                new Point3D(0.0d, 0.0d, 3.0d));

        System.out.println("Test 9 : the Ray begin between the extension of the edge of the Triangle");
        Ray r9 = new Ray(new Point3D(1.0d, 1.0d, 1.0d), new Vector(-1.0d, -1.0d, -1.0d));
        Point3D p5 = new Point3D(1.0d, 1.0d, 1.0d);

        assertEquals(p5, t2.findIntersections(r9),"wrong Test 9 ");

    }






}