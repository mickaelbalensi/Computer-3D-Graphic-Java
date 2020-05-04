package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

public class Tube extends RadialGeometry {
    public Ray _axisRay;

    /**
     *
     * @param ray
     * @param radius
     */
    public Tube(Ray ray, double radius){
        super(radius);
        _axisRay=ray;
    }

    /**
     *
     * @param tube
     */
    public Tube(Tube tube){
        super(tube._radius);
        _axisRay=tube._axisRay;
    }


    /**
     *
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
     *
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
