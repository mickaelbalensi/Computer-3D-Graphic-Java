package geometries;

import primitives.*;

import javax.imageio.ImageTranscoder;
import java.util.List;

/**
 * the geometries.Tube class represents the geometry Tube
 */
public class Tube extends RadialGeometry {
    public Ray _axisRay;

    /**
     * Constructor who takes double, Ray, Color and Material
     * @param radius (double)
     * @param ray (Ray)
     * @param color (Color)
     * @param material (Material)
     */
    public Tube(Ray ray, double radius, Color color, Material material){
        super(radius,color,material);
        setAxisRay(ray);
    }

    /**
     * Constructor who takes double, Ray, Color
     * @param radius (double)
     * @param ray (Ray)
     * @param color (Color)
     */
    public Tube(Ray ray, double radius, Color color){
        super(radius,color);
        setAxisRay(ray);
    }

    /**
     * Ctor without color
     * @param ray
     * @param radius
     */
    public Tube(Ray ray, double radius){
        super(radius);
        setAxisRay(ray);
    }


    /**
     * the geometries.Tube copy-constructor receiving tube
     * @param tube by type Tube
     */
    public Tube(Tube tube){
        super(tube._radius,tube._emission,tube._material);
        setAxisRay(tube._axisRay);
    }

    @Override
    public Vector getNormal(Point3D pt) {
        double xRayVec=_axisRay.getDirection().getPt().getX().get();
        double yRayVec=_axisRay.getDirection().getPt().getY().get();
        double zRayVec=_axisRay.getDirection().getPt().getZ().get();

        double xRayPt=_axisRay.getPt().getX().get();
        double yRayPt=_axisRay.getPt().getY().get();
        double zRayPt=_axisRay.getPt().getZ().get();

        double xPt=pt.getX().get();
        double yPt=pt.getY().get();
        double zPt=pt.getZ().get();

        double t= (xRayVec*(xPt-xRayPt)+yRayVec*(yPt-yRayPt)+zRayVec*(zPt-zRayPt))/(xRayVec*xRayVec+yRayVec*yRayVec+zRayVec*zRayVec);

        Vector normal=new Vector(xPt -xRayPt -xRayVec *t,yPt -yRayPt -yRayVec *t,zPt -zRayPt -zRayVec *t);
        return normal.normalize();
    }

    /**
     * the getter of ray of the Tube
     * @return ray by type Ray
     */
    public Ray get_axisRay() {
        return _axisRay;
    }

    @Override
    public String toString() {
        return "Tube{" +
                "_axisRay=" + _axisRay +
                ", _radius=" + _radius +
                '}';
    }


    @Override
    public List<GeoPoint> findIntersections(Ray ray, double max) {
        return null;
    }

    public void setAxisRay(Ray _axisRay) {
        this._axisRay = _axisRay;
    }
}
