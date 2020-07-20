package geometries;

import primitives.Point3D;
import primitives.Vector;
/**
 * this class geometries.Geometry represents all of geometric shapes
 * this class is abstract,
 * Geometry implements the Intersectable's interface
 */
public interface Geometry extends Intersectable {
    public Vector getNormal(Point3D pt);



}
