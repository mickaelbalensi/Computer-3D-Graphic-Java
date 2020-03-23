package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class Tube extends RadialGeometry {
    public Ray _axisRay;

    public Tube(Ray ray, RadialGeometry radius){
        super(radius);
        _axisRay=ray;
    }

    public Tube(Tube tube){
        super(tube._radius);
        _axisRay=tube._axisRay;
    }

    @Override
    public Vector getNormal(Point3D pt) {
        return null;
    }

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


}
