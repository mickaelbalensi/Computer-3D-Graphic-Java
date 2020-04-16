package geometries;

import primitives.*;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<Point3D> findIntersections(Ray ray){
        Vector hypothesis= this._center.subtract(ray.getPt());
        double adjacent_side = ray.getDirection().dotProduct(hypothesis);
        double opposite_side = Math.sqrt(hypothesis.lengthSquared() - adjacent_side*adjacent_side);


        if(opposite_side> this._radius)
            return null;
        else {
            List<Point3D> intersectionsPoints = new ArrayList<Point3D>();
            if (opposite_side == this._radius) {
                intersectionsPoints.add(ray.getPt().add(ray.getDirection().scale(adjacent_side)));
            } else {
                double side =  Math.sqrt(this._radius*this._radius - opposite_side*opposite_side);
                if (hypothesis.length()> this._radius){
                    intersectionsPoints.add(ray.getPt().add(ray.getDirection().scale(adjacent_side-side)));
                }
                intersectionsPoints.add(ray.getPt().add(ray.getDirection().scale(adjacent_side+side)));
            }
            return intersectionsPoints;
        }
    }
}
