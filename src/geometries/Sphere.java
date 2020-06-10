package geometries;

import primitives.*;
import java.util.ArrayList;
import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 *  the geometries.Sphere class represents geometry sphere
 *  this class extends geometries.RadialGeometry for his radius
 */
public class Sphere extends RadialGeometry {
    public Point3D _center;

    //region CTORs
    /**
     * Constructor for a sphere receiving :
     * @param center it's center (Point3D)
     * @param radius it's radius (double)
     * @param emissionColor it's color (Color)
     * @param material it's material
     */
    public Sphere(Point3D center, double radius,Color emissionColor, Material material){
        super(radius,emissionColor,material);
        setCenter(center);
    }

    public Sphere(Color emissionColor,Material material, double radius,Point3D center){
        this(center,radius,emissionColor,material);
    }


    /**
     * Constructor who takes Point3D doubles and  Color
     * @param center (Point3D)
     * @param radius (double)
     * @param emissionColor (Color)
     */
    public Sphere(Point3D center, double radius,Color emissionColor){
        super(radius,emissionColor);
        setCenter(center);
    }
    /**
     * the geometries.Sphere constructor receiving the middle Point of the sphere and his radius
     * @param center the center Point3D
     * @param radius by type double     
     */    
    public Sphere (Point3D center,double radius){
        super(radius);
        setCenter(center);
    }
    //endregion

    /**
     * the getter of the center of the Sphere
     * @return center by type Point3D
     */
    public Point3D getCenter() {
        return _center;
    }
    @Override
    public Vector getNormal(Point3D pt) {
        return pt.subtract(this._center).normalize();
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "_center=" + _center +
                ", _radius=" + _radius +
                '}';
    }

    @Override
    public List<GeoPoint> findIntersections(Ray ray) {
        Vector u;

        try {
            //first step calculate of  vector u refer to the recitation 3
            u = _center.subtract(ray.getPt());
        } catch (IllegalArgumentException e) {
            return List.of(new GeoPoint(this, (ray.getTargetPoint(this._radius))));
        }
        double tm = ray.getDirection().dotProduct(u);//calculate of the distance Tm
        double distance = alignZero(Math.sqrt(Math.abs(u.lengthSquared() - (tm * tm))));
        if (distance >= _radius)
            return null;

        double th = Math.sqrt(((_radius * _radius) - (distance * distance)));
        if (isZero(th)) return null;

        double t1 = alignZero(tm + th);
        if (t1 > 0) {
            double t2 = alignZero(tm - th);
            if (t2 > 0)
                return List.of(new GeoPoint(this,ray.getTargetPoint(t1)),new GeoPoint(this,ray.getTargetPoint(t2)));
            else
                return List.of(new GeoPoint(this,ray.getTargetPoint(t1)));
        }
        return null;
    }

/*    public List<GeoPoint> findIntersections(Ray ray) {

        List<GeoPoint> intersectionsPoints = null;

        if (ray.getPt().equals(this._center)) {//if the ray starts in the middle
            intersectionsPoints = new ArrayList<GeoPoint>();
            intersectionsPoints.add(new GeoPoint(this,ray.getPt().add(ray.getDirection().scale(this._radius))));
        }
        else{
            Vector hypothesis = this._center.subtract(ray.getPt());

            double adjacent_side = ray.getDirection().dotProduct(hypothesis);
            double opposite_side = Math.sqrt(hypothesis.lengthSquared() - adjacent_side * adjacent_side);
            double angleRayHypo = Math.acos(ray.getDirection().dotProduct(hypothesis) / (ray.getDirection().length() * hypothesis.length()));

            if (hypothesis.length() > this._radius) {  //if the ray starts outside the sphere

                if (!( opposite_side >= this._radius || Math.toDegrees(angleRayHypo) >= 90 ))  {
                    // if the ray's line don't crosses the sphere or if the ray is tangent
                    intersectionsPoints = new ArrayList<GeoPoint>();

                    //if (opposite_side == this._radius)    //if the ray's line crosses the sphere only in one Point
                    //    intersectionsPoints.add(ray.getPt().add(ray.getDirection().scale(adjacent_side)));

                    //else
                    {
                        double side = Math.sqrt(this._radius * this._radius - opposite_side * opposite_side);
                        intersectionsPoints.add(new GeoPoint(this,ray.getPt().add(ray.getDirection().scale(adjacent_side - side))));
                        intersectionsPoints.add(new GeoPoint(this,ray.getPt().add(ray.getDirection().scale(adjacent_side + side))));
                    }
                }

            } else if (!(hypothesis.length() == this._radius && Math.toDegrees(angleRayHypo) >= 90)) {//if ray's line starts at the sphere

                    intersectionsPoints = new ArrayList<GeoPoint>();
                    double side = Math.sqrt(this._radius * this._radius - opposite_side * opposite_side);
                    intersectionsPoints.add(new GeoPoint(this,ray.getPt().add(ray.getDirection().scale(adjacent_side + side))));
                }
        }
        return intersectionsPoints;
    }*/



    public void setCenter(Point3D _center) {
        this._center = _center;
    }
}
