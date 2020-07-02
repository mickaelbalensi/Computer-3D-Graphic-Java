package geometries;

import primitives.*;

import java.util.List;

/**
 * the geometries.Tube class represents the geometry Tube
 */
public class Tube extends RadialGeometry {
    protected Ray axisRay;

    /**
     * Constructor who takes double, Ray, Color and Material
     *
     * @param radius   (double)
     * @param ray      (Ray)
     * @param color    (Color)
     * @param material (Material)
     */
    public Tube(Ray ray, double radius, Color color, Material material) {
        super(radius, color, material);
        axisRay = ray;
        Xmax = MAX;
        Ymax = MAX;
        Zmax = MAX;
        Xmin = MIN;
        Ymin = MIN;
        Zmin = MIN;
    }

    /**
     * Constructor who takes double, Ray, Color
     *
     * @param radius (double)
     * @param ray    (Ray)
     * @param color  (Color)
     */
    public Tube(Ray ray, double radius, Color color) {
        this(ray, radius, color, Material.DEFAULT);
    }

    /**
     * Ctor without color
     *
     * @param ray
     * @param radius
     */
    public Tube(Ray ray, double radius) {
        this(ray, radius, Color.BLACK);
    }

    @Override
    public Vector getNormal(Point3D pt) {
        double xRayVec = axisRay.getDirection().getPt().getX().get();
        double yRayVec = axisRay.getDirection().getPt().getY().get();
        double zRayVec = axisRay.getDirection().getPt().getZ().get();

        double xRayPt = axisRay.getPt().getX().get();
        double yRayPt = axisRay.getPt().getY().get();
        double zRayPt = axisRay.getPt().getZ().get();

        double xPt = pt.getX().get();
        double yPt = pt.getY().get();
        double zPt = pt.getZ().get();

        double t = (xRayVec * (xPt - xRayPt) + yRayVec * (yPt - yRayPt) + zRayVec * (zPt - zRayPt)) / (xRayVec * xRayVec + yRayVec * yRayVec + zRayVec * zRayVec);

        Vector normal = new Vector(xPt - xRayPt - xRayVec * t, yPt - yRayPt - yRayVec * t, zPt - zRayPt - zRayVec * t);
        return normal.normalize();
    }

    /**
     * the getter of ray of the Tube
     *
     * @return ray by type Ray
     */
    public Ray get_axisRay() {
        return axisRay;
    }

    @Override
    public String toString() {
        return "Tube{" +
                "_axisRay=" + axisRay +
                ", _radius=" + radius +
                '}';
    }

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double max) {
        return null;
    }
}
