package geometries;

import primitives.*;

public class Sphere extends RadialGeometry {
    public Point3D _center;

    public Point3D get_center() {
        return _center;
    }

    public Sphere(Point3D pt, double radius){
        super(radius);
        this._center=pt;
    }

    @Override
    public Vector getNormal(Point3D pt) {
        return new Vector(pt.subtract(this._center)).normalize();
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "_center=" + _center +
                ", _radius=" + _radius +
                '}';
    }
}
