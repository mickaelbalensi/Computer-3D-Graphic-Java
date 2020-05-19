package geometries;

import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

public class Tube extends RadialGeometry {
    public Ray _axisRay;

    /**
     * Constructor who takes double, Ray, Color and Material
     * @param radius (double)
     * @param ray (Ray)
     * @param color (Color)
     */
    public Tube(Ray ray, double radius, Color color){
        super(radius,color);
        _axisRay=ray;
    }

    /**
     * @param tube
     */
    public Tube(Tube tube,Color color){
        super(tube._radius,color);
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
    public List<GeoPoint> findIntersections(Ray ray) {
        return null;
    }
}
