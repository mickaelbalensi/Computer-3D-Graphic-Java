package geometriesTest;

import org.junit.jupiter.api.Test;
import geometries.*;
import geometries.Intersectable.*;
import primitives.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GeometriesTest {
    /**
     * Test method for {@link geometries.Geometries#findIntersections(Ray)}.
     */
    @Test
    public void findIntersections() {
        //region ============ Equivalence Partitions Tests ==============
        //TC01 : Several forms (but not all) are truncated
        Geometries shapes1 = new Geometries(new Sphere(new Point3D(2, 1, 4), 3), new Sphere(new Point3D(8, 12, 4), 3),
                new Triangle(new Point3D(3.56, 7.53, 0.26), new Point3D(0.71, 6.78, 7.68), new Point3D(2.14, 11.26, 0)));
        List<Point3D> pointsIntersections1 = shapes1.findIntersections(new Ray(new Point3D(-6, -4, 2), new Vector(17.19, 17.87, 0.63)));
        assertEquals(pointsIntersections1.size(), 4, "TC01 : Several forms (but not all) are truncated");
        //endregion
        //region ================ Boundary Values Tests =================
        //TC02 : Empty list of forms
        Geometries shapes2 = new Geometries();
        List<Point3D> pointsIntersections2 = shapes2.findIntersections(new Ray(new Point3D(-6, -4, 2), new Vector(17.19, 17.87, 0.63)));
        assertNull(pointsIntersections2, "TC02 : Empty list of forms");

        //TC03 : No Shape is cropped (0 points Intersection)
        Geometries shapes3 = shapes1;
        List<Point3D> pointsIntersections3 = shapes3.findIntersections(new Ray(new Point3D(8, 3, 4), new Vector(-13, 3, -1)));
        assertNull(pointsIntersections3, "TC03 : No Shape is cropped (0 points Intersection)");

        //TC04 : Only one shape is cropped
        Geometries shapes4 = shapes1;
        List<Point3D> pointsIntersections4 = shapes4.findIntersections(new Ray(new Point3D(8, 7, 0), new Vector(-11, 3, 4)));
        assertEquals(1, pointsIntersections4.size(), "TC04 : Only one shape is cropped");

        //TC05 : All shapes are truncated
        Geometries shapes5 = new Geometries(new Sphere(new Point3D(2, 1, 4), 3), new Sphere(new Point3D(8, 12, 4), 3),
                new Triangle(new Point3D(1, 6, 0), new Point3D(5, 5, 0), new Point3D(5, 7, 8)));
        List<Point3D> pointsIntersections5 = shapes5.findIntersections(new Ray(new Point3D(-6, -4, 2), new Vector(17.19, 17.87, 0.63)));
        assertEquals(5, pointsIntersections5.size(), "TC05 : All shapes are truncated");
        //end region
    }
}