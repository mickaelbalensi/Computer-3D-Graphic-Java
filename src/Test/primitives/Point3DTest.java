package primitives;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Point3DTest {
    /**
     * Test method for {@link primitives.Point3D#subtract(Point3D pt)}.
     */
    @Test
    void subtract() {
        Point3D v1 = new Point3D(1.0D, 2.0D, 3.0D);
        Point3D v2 = new Point3D(3.0D, 4.0D, 5.0D);
        Point3D v3 = new Point3D(-3.0D, 4.0D, 5.0D);
        Point3D v4 = new Point3D(3.0D, -4.0D, 5.0D);
        Point3D v5 = new Point3D(3.0D, 4.0D, -5.0D);
        Point3D v6 = new Point3D(-3.0D, -4.0D, 5.0D);
        Point3D v7 = new Point3D(3.0D, -4.0D, -5.0D);
        Point3D v8 = new Point3D(-3.0D, -4.0D, -5.0D);
        Vector expResult21 = new Vector(-2.0D, -2.0D, -2.0D);
        Vector result21 = v1.subtract(v2);
        Assert.assertTrue("subtract Error", result21.equals(expResult21));
        Vector expResult31 = new Vector(4.0D, -2.0D, -2.0D);
        Vector result31 = v1.subtract(v3);
        Assert.assertTrue("subtract Error", result31.equals(expResult31));
        Vector expResult41 = new Vector(-2.0D, 6.0D, -2.0D);
        Vector result41 = v1.subtract(v4);
        Assert.assertTrue("subtract Error", result41.equals(expResult41));
        Vector expResult51 = new Vector(-2.0D, -2.0D, 8.0D);
        Vector result51 = v1.subtract(v5);
        Assert.assertTrue("subtract Error", result51.equals(expResult51));
        Vector expResult61 = new Vector(4.0D, 6.0D, -2.0D);
        Vector result61 = v1.subtract(v6);
        Assert.assertTrue("subtract Error", result61.equals(expResult61));
        Vector expResult71 = new Vector(-2.0D, 6.0D, 8.0D);
        Vector result71 = v1.subtract(v7);
        Assert.assertTrue("subtract Error", result71.equals(expResult71));
        Vector expResult81 = new Vector(4.0D, 6.0D, 8.0D);
        Vector result81 = v1.subtract(v8);
        Assert.assertTrue("subtract Error", result81.equals(expResult81));
    }
    /**
     * Test method for {@link primitives.Point3D#add(Vector vec)}.
     */
    @Test
    void add() {
        Point3D v1 = new Point3D(1.0D, 2.0D, 3.0D);
        Vector v2 = new Vector(3.0D, 4.0D, 5.0D);
        Vector v3 = new Vector(-3.0D, 4.0D, 5.0D);
        Vector v4 = new Vector(3.0D, -4.0D, 5.0D);
        Vector v5 = new Vector(3.0D, 4.0D, -5.0D);
        Vector v6 = new Vector(-3.0D, -4.0D, 5.0D);
        Vector v7 = new Vector(3.0D, -4.0D, -5.0D);
        Vector v8 = new Vector(-3.0D, -4.0D, -5.0D);
        Point3D expResult21 = new Point3D(4.0D, 6.0D, 8.0D);
        Point3D result21 = v1.add(v2);
        Assert.assertTrue("add Error v1 and v2", result21.equals(expResult21));
        Point3D expResult31 = new Point3D(-2.0D, 6.0D, 8.0D);
        Point3D result31 = v1.add(v3);
        Assert.assertTrue("add Error v1 and v3", result31.equals(expResult31));
        Point3D expResult41 = new Point3D(4.0D, -2.0D, 8.0D);
        Point3D result41 = v1.add(v4);
        Assert.assertTrue("add Error v1 and v4", result41.equals(expResult41));
        Point3D expResult51 = new Point3D(4.0D, 6.0D, -2.0D);
        Point3D result51 = v1.add(v5);
        Assert.assertTrue("add Error v1 and v5", result51.equals(expResult51));
        Point3D expResult61 = new Point3D(-2.0D, -2.0D, 8.0D);
        Point3D result61 = v1.add(v6);
        Assert.assertTrue("add Error v1 and v6", result61.equals(expResult61));
        Point3D expResult71 = new Point3D(4.0D, -2.0D, -2.0D);
        Point3D result71 = v1.add(v7);
        Assert.assertTrue("add Error v1 and v7", result71.equals(expResult71));
        Point3D expResult81 = new Point3D(-2.0D, -2.0D, -2.0D);
        Point3D result81 = v1.add(v8);
        Assert.assertTrue("add Error v1 and v8", result81.equals(expResult81));
    }
    /**
     * Test method for {@link primitives.Point3D#distanceSquared(Point3D pt)}.
     */
    @Test
    void distanceSquared() {
        Point3D v1 = new Point3D(1.0D, 2.0D, 3.0D);
        Point3D v2 = new Point3D(3.0D, 4.0D, 5.0D);
        Point3D v3 = new Point3D(-3.0D, 4.0D, 5.0D);
        Point3D v4 = new Point3D(3.0D, -4.0D, 5.0D);
        Point3D v5 = new Point3D(3.0D, 4.0D, -5.0D);
        Point3D v6 = new Point3D(-3.0D, -4.0D, 5.0D);
        Point3D v7 = new Point3D(3.0D, -4.0D, -5.0D);
        //new Point3D(-3.0D, -4.0D, -5.0D);
        double expDistance2 = v2.distanceSquared(v1);
        double distance2 = 12.0D;
        Assert.assertEquals("subtract Error", distance2, expDistance2, 0.0D);
        double expDistance3 = v3.distanceSquared(v1);
        double distance3 = 24.0D;
        Assert.assertEquals("subtract Error", distance3, expDistance3, 0.0D);
        v4.distanceSquared(v1);
        double distance4 = 44.0D;
        Assert.assertEquals("subtract Error", distance3, expDistance3, 0.0D);
        double expDistance5 = v5.distanceSquared(v1);
        double distance5 = 72.0D;
        Assert.assertEquals("subtract Error", distance5, expDistance5, 0.0D);
        double expDistance6 = v6.distanceSquared(v1);
        double distance6 = 56.0D;
        Assert.assertEquals("subtract Error", distance6, expDistance6, 0.0D);
        double expDistance7 = v7.distanceSquared(v1);
        double distance7 = 104.0D;
        Assert.assertEquals("subtract Error", distance7, expDistance7, 0.0D);
    }
}