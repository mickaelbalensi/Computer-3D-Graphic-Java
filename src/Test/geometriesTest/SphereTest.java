package geometriesTest;

import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.*;
import geometries.Intersectable.GeoPoint;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SphereTest {
    /**
     * Test method for {@link geometries.Sphere#getNormal(Point3D pt)}.
     */
    @Test
    public void getNormalTest() {
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

    /**
     * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
     */
    @Test
    public void findIntersectionsTest(){

        //region ============ Equivalence Partitions Tests ==============
        //region TC01: Ray's line is outside the sphere (0 points)

        assertEquals(new Sphere(new Point3D(1, 0, 0), 1d).findIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(1, 1, 0))),
                null, "TC01:Ray's line out of sphere");
        //endregion
        //region TC02: Ray starts before and crosses the sphere (2 points)


        Point3D p1 = new Point3D(0.0651530771650466, 0.355051025721682, 0);
        Point3D p2 = new Point3D(1.53484692283495, 0.844948974278318, 0);

        List<GeoPoint> result = new Sphere(new Point3D(1, 0, 0), 1d)
                .findIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(3, 1, 0)));
        assertEquals(2, result.size(),"TC02: Wrong number of points");

        if (result.get(0).point.getX().get() > result.get(1).point.getX().get())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(p1, p2), result,"TC02: Ray crosses sphere");
        //endregion
        //region TC03: Ray starts inside the sphere (1 point)

        Point3D p3 = new Point3D(3, 2, 0);
        List<GeoPoint> result2 = new Sphere(new Point3D(2, 2, 0), 1d)
                .findIntersections(new Ray(new Point3D(2, 1.5, 0), new Vector(1, 0.5, 0)));
        assertEquals(1, result2.size(),"TC03: Wrong number of points");

        assertEquals(p3, result2.get(0),"TC03: Not the good Point p3");

        //endregion
        //region TC03bis
        double t=0.5*(Math.sqrt(14)-2);
        Point3D p3b = new Point3D(2-t, 4+t, 0);
        List<GeoPoint> result2b = new Sphere(new Point3D(4, 4, 0), 3d)
                .findIntersections(new Ray(new Point3D(2, 4, 0), new Vector(-1, 1, 0)));
        assertEquals(1, result2b.size(),"TC03b: Wrong number of points");

        assertEquals(p3b, result2b.get(0),"TC03b: Not the good Point p3");

        //endregion
        //region TC04: Ray starts after the sphere (0 points)

        List<GeoPoint> result4 = new Sphere(new Point3D(2, 2, 0), 1d)
                .findIntersections(new Ray(new Point3D(0.5, 2, 0), new Vector(-2, 0.5, 0)));
        assertTrue(result4==null,"TC04: Wrong number of points");
        //endregion
        //endregion
        //region =============== Boundary Values Tests ==================
        // **** Group: Ray's line crosses the sphere (but not the center)
        //region TC11: Ray starts at sphere and goes inside (1 points)
        Point3D p11 = new Point3D(2, 1, 0);
        Ray r11 = new Ray(new Point3D(1, 2, 0), new Vector(1, -1, 0));
        Sphere s11 = new Sphere(new Point3D(2, 2, 0), 1d);
        List<GeoPoint> result11 = s11.findIntersections(r11);
        assertEquals(1, result11.size(),"TC11: Wrong number of points");

        assertEquals(p11, result11.get(0),"TC11: Not the same Point p11");

        assertEquals(r11.getPt().subtract(s11._center).length(),s11.get_radius(),"TC11: the Point is not on the circle");
        //endregion
        //region TC12: Ray starts at sphere and goes outside (0 points)
        List<GeoPoint> result12 = new Sphere(new Point3D(2, 2, 0), 1d)
                .findIntersections(new Ray(new Point3D(1, 2, 0), new Vector(-2, 0.5, 0)));
        assertTrue(result12==null,"TC12: Wrong number of points");
        //endregion
        // **** Group: Ray's line goes through the center
        //region TC13: Ray starts before the sphere (2 points)
        Point3D pt13a = new Point3D(2,1,0);
        Point3D pt13b = new Point3D(2,3,0);
        List<GeoPoint> res13 = new Sphere(new Point3D(2,2,0), 1d).findIntersections(new Ray(new Point3D(2,4,0), new Vector(0,-1,0)));
        assertEquals(2,res13.size(),"TC13: Wrong number of points");
        if (res13.get(0).point.getY().get()> res13.get(1).point.getY().get())
            res13 = List.of(res13.get(1),res13.get(0));
        assertEquals(List.of(pt13a,pt13b), res13, " TC13: Ray crosses sphere");
        //endregion
        //region TC14: Ray starts at sphere and goes inside (1 points)
        Point3D pt14 = new Point3D(2,1,0);
        List<GeoPoint> res14 = new Sphere(new Point3D(2,2,0), 1d).findIntersections(new Ray(new Point3D(2,3,0), new Vector(0,-1,0)));
        assertEquals(1,res14.size(),"TC14: Wrong number of points");
        assertEquals(pt14, res14.get(0), " TC14: Ray crosses sphere");
        //endregion
        //region TC15: Ray starts inside (1 points)
        Point3D pt15 = new Point3D(2,1,0);
        List<GeoPoint> res15 = new Sphere(new Point3D(2,2,0), 1d).findIntersections(new Ray(new Point3D(2,2.5,0), new Vector(0,-1,0)));
        assertEquals(1,res15.size(),"TC15 : Wrong number of points");
        assertEquals(pt15, res15.get(0), " TC15 : Ray crosses sphere");
        //endregion
        //region TC16: Ray starts at the center (1 points)
        Point3D pt16 = new Point3D(2,1,0);
        List<GeoPoint> res16 = new Sphere(new Point3D(2,2,0), 1d).findIntersections(new Ray(new Point3D(2,2,0), new Vector(0,-1,0)));
        assertEquals(1,res16.size(),"TC16 : Wrong number of points");
        assertEquals(pt16, res16.get(0), " TC16 : Ray crosses sphere");
        //endregion
        //region TC17: Ray starts at sphere and goes outside (0 points)
        List<GeoPoint> res17 = new Sphere(new Point3D(2, 2, 0), 1d)
                .findIntersections(new Ray(new Point3D(2, 1, 0), new Vector(0,-1,0)));
        assertTrue(res17==null,"TC12: Wrong number of points");

        //endregion
        //region TC18: Ray starts after sphere (0 points)
        List<GeoPoint> res18 = new Sphere(new Point3D(2, 2, 0), 1d)
                .findIntersections(new Ray(new Point3D(2, 0.5, 0), new Vector(0,-1,0)));
        assertTrue(res18==null,"TC12: Wrong number of points");

        //endregion
        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        //region TC19: Ray starts before the tangent point
        Ray r19 =new Ray(new Point3D(1,3,0), new Vector(1,0,0));
        Sphere s19= new Sphere(new Point3D(2, 2, 0), 1d);
        List<GeoPoint> res19= s19.findIntersections(r19);
        assertTrue(res19==null,"TC19: Wrong number of points");
        //endregion
        //region TC20: Ray starts at the tangent point
        List<GeoPoint> res20 = new Sphere(new Point3D(2, 2, 0), 1d)
                .findIntersections(new Ray(new Point3D(2, 1, 0), new Vector(1,0,0)));
        assertTrue(res20==null,"TC20: Wrong number of points");
        //endregion
        //region TC21: Ray starts after the tangent point
        List<GeoPoint> res21 = new Sphere(new Point3D(2, 2, 0), 1d)
                .findIntersections(new Ray(new Point3D(3, 3, 0), new Vector(1,0,0)));
        assertTrue(res21==null,"TC21: Wrong number of points");
        //endregion
        // **** Group: Special cases
        //region TC22: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        List<GeoPoint> res22 = new Sphere(new Point3D(2, 2, 0), 1d).findIntersections(new Ray(new Point3D(0,2,0), new Vector(0,1,0)));
        assertTrue(res22==null,"TC22: Wrong number of points");

        //endregion
        //endregion
    }
    @Test
    public void findIntersectionsTest2() {
        Point3D p1 = new Point3D(-0.16, 2.07, 2.21);
        Point3D p2 = new Point3D(0.91, 3.18, 2.25);

        List<GeoPoint> result = new Sphere(new Point3D(2, 1, 4), 3d)
                .findIntersections(new Ray(new Point3D(-6, -4, 2), new Vector(17.19, 17.87, 0.63)));
        assertEquals(2, result.size(), "TC02: Wrong number of points");

        if (result.get(0).point.getX().get() > result.get(1).point.getX().get())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(p1, p2), result, "TC02: Ray crosses sphere");
    }

}