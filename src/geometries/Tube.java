package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * the geometries.Tube class represents the geometry Tube
 */
public class Tube extends RadialGeometry {
    public Ray _axisRay;

    /**
     * Ctor
     * @param ray ray
     * @param radius double
     */
    public Tube(Ray ray, double radius){
        super(radius);
        _axisRay=ray;
    }

    /**
     * Copy Ctor
     * @param tube Tube
     */
    public Tube(Tube tube){
        super(tube._radius);
        _axisRay=tube._axisRay;
    }


    /**
     * The getNormal
     * @param pt which the point pointed by the vector
     * @return Point3D if there is an intersection between the vector and the tube
     */
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
     * getter of axisray
     * @return ray
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
    public List<Point3D> findIntersections(Ray ray) {
        return null;
    }
}
