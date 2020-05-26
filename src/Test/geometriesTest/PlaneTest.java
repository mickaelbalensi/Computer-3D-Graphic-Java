package geometriesTest;

import geometries.Plane;
import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTest {
    /**
     * Test method for {@link geometries.Plane#getNormal(Point3D pt)}.
     */
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


    @Test
    public void testFindIntersection2() {
        Plane p1;
        Ray r1;
        ArrayList<Point3D> a1 = new ArrayList<>();

        // Test 1
        System.out.println("Test 1 : Ray intersect the plane");
        r1 = new Ray(new Point3D(3.0d, 3.0d, 3.0d), new Vector(-1.0d, 0.0d, -1.0d));
        p1 = new Plane(new Point3D(5.0d, 4.0d, 0.0d),
                new Point3D(7.0d, 8.0d, 0.0d),
                new Point3D(5.0d, 6.0d, 0.0d));
        a1.add(new Point3D(0.0d, 3.0d, 0.0d));
        assertEquals(p1.findIntersections(r1), a1);

        // Test 2
        System.out.println("Test 2 : Ray doesn't intersect the plane");
        r1 = new Ray(new Point3D(3.0d, 3.0d, 3.0d), new Vector(1.0d, 0.0d, 1.0d));
        a1.clear();
        assertNull(p1.findIntersections(r1));

        //Test 3
        System.out.println("Test 3 : collinear vector who start before the plane");
        r1 = new Ray(new Point3D(0.0d, 0.0d, 0.0d), new Vector(0.0d, 0.0d, 1.0d));
        p1 = new Plane(new Point3D(0.0d, 0.0, 1.0d),
                new Point3D(1.0d, 0.0d, 1.0d),
                new Point3D(0.0d, 1.0d, 1.0d));
        a1.clear();
        a1.add(new Point3D(0.0d, 0.0d, 1.0d));
        assertEquals(p1.findIntersections(r1), a1);

        // Test 4
        //System.out.println("Test 4 : collinear vector who start into the plane");
        //r1 = new Ray(new Point3D(0.0d, 0.0d, 1.0d), new Vector(0.0d, 0.0d, 2.0d));
        //a1.clear();
        //a1.add(new Point3D(0.0d, 0.0d, 1.0d));
        //assertEquals(p1.findIntersections(r1), a1);

        // Test 5
        System.out.println("Test 5 : collinear vector who start after the plane");
        r1 = new Ray(new Point3D(0.0d, 0.0d, 2.0d), new Vector(0.0d, 0.0d, 3.0d));
        assertNull(p1.findIntersections(r1));

        // Test 6
        System.out.println("Test 6 : parallel vector");
        r1 = new Ray(new Point3D(0.0d, 0.0d, 0.0d), new Vector(1.0d, 0.0d, 0.0d));
        assertNull(p1.findIntersections(r1));

        // Test 7
        // System.out.println("Test 7 : included vector");
        //r1 = new Ray(new Point3D(0.0d, 0.0d, 1.0d), new Vector(1.0d, 0.0d, 1.0d));
        //assertEquals(p1.findIntersections(r1), a1);

        // Test 8
        System.out.println("Test 8 : secant vector who start into the plane");
        r1 = new Ray(new Point3D(1.0d, 1.0d, 1.0d), new Vector(1.0, 1.0, 1.0));
        a1.clear();
        a1.add(new Point3D(1.0d, 1.0d, 1.0d));
        assertEquals(p1.findIntersections(r1), a1);
    }

    @Test
    public void testFindIntersection() {
        Plane p1;
        Ray r1;
        ArrayList<Point3D> a1 = new ArrayList<>();

        // Test 1
        System.out.println("Test 1 : Ray intersect the plane");
        r1 = new Ray(new Point3D(3.0d, 3.0d, 3.0d), new Vector(-1.0d, 0.0d, -1.0d));
        p1 = new Plane(new Point3D(5.0d, 4.0d, 0.0d),
                new Point3D(7.0d, 8.0d, 0.0d),
                new Point3D(5.0d, 6.0d, 0.0d));
        a1.add(new Point3D(0.0d, 3.0d, 0.0d));
        assertEquals(p1.findIntersections(r1), a1);

        // Test 2
        System.out.println("Test 2 : Ray doesn't intersect the plane");
        Plane plane2= new Plane(new Point3D(0,0,0),new Point3D(1,0,0),new Point3D(0,1,0));
        assertNull(plane2.findIntersections(new Ray(new Point3D(0,0,1),new Vector(1,1,4))),"wrong Test2");


        //Test 3
        System.out.println("Test 3 : collinear vector who start before the plane");
        r1 = new Ray(new Point3D(0.0d, 0.0d, 0.0d), new Vector(0.0d, 0.0d, 1.0d));
        p1 = new Plane(new Point3D(0.0d, 0.0, 1.0d),
                new Point3D(1.0d, 0.0d, 1.0d),
                new Point3D(0.0d, 1.0d, 1.0d));
        a1.clear();
        a1.add(new Point3D(0.0d, 0.0d, 1.0d));
        assertEquals(p1.findIntersections(r1), a1);

        // Test 4
        System.out.println("Test 4 : collinear vector who start into the plane");
        r1 = new Ray(new Point3D(0.0d, 0.0d, 1.0d), new Vector(0.0d, 0.0d, 2.0d));
        a1.clear();
        a1.add(new Point3D(0.0d, 0.0d, 1.0d));
        //assertEquals(p1.findIntersections(r1), a1);

        // Test 5
        System.out.println("Test 5 : collinear vector who start after the plane");
        r1 = new Ray(new Point3D(0.0d, 0.0d, 2.0d), new Vector(0.0d, 0.0d, 3.0d));
        assertNull(p1.findIntersections(r1));

        // Test 6
        System.out.println("Test 6 : parallel vector");
        r1 = new Ray(new Point3D(0.0d, 0.0d, 0.0d), new Vector(1.0d, 0.0d, 0.0d));
        assertNull(p1.findIntersections(r1));

        // Test 7
        //System.out.println("Test 7 : included vector");
        //r1 = new Ray(new Point3D(0.0d, 0.0d, 1.0d), new Vector(1.0d, 0.0d, 1.0d));
        //assertEquals(p1.findIntersections(r1), a1);

        // Test 8
        System.out.println("Test 8 : secant vector who start into the plane");
        r1 = new Ray(new Point3D(1.0d, 1.0d, 1.0d), new Vector(1.0, 1.0, 1.0));
        a1.clear();
        a1.add(new Point3D(1.0d, 1.0d, 1.0d));
        //assertEquals(p1.findIntersections(r1), a1);
    }
}
