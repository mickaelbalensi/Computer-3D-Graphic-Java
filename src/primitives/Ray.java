package primitives;

import geometries.Intersectable;

import static primitives.Util.isZero;
import geometries.Intersectable;

/**
 * this class represents a vector which start form a beginning point
 */
public class Ray {
    protected Point3D pt;
    protected Vector direction;
    private static final double DELTA = 0.1;

    /**
     * the begin is the beginning point of our vector and the direction represents the vector's direction
     * @param begin
     * @param direction
     * @param normal
     */
    public Ray(Point3D begin, Vector direction, Vector normal) {
        this.pt=begin.add(normal.scale(normal.dotProduct(direction) > 0 ? DELTA : -DELTA));
        this.direction=direction.normalized();
    }

    /**
     *
     * @param pt the beginning point of our vector
     * @param vec the direction of the vector going from the point
     */
    public Ray(Point3D pt, Vector vec) {
        this.pt=pt;
        this.direction=vec.normalized();
    }

    /**
     * @param r now our ray leaves from the point and is directed by our vector
     */
    public  Ray(Ray r){
        this.pt=r.pt;
        this.direction=r.direction;
    }

    /**
     *
     * @return Point3D which is our beginning point
     */
    public Point3D getPt() {
        return pt;
    }

    /**
     * @return Vector which is our direction
     */
    public Vector getDirection() {
        return direction;
    }

    /**
     * @param lenght
     * @return the end point of the vector
     */
    public Point3D getTargetPoint(double lenght){
        if (isZero(lenght))return pt;
        else
            return pt.add(direction.scale(lenght));
    }
    @Override
    public String toString() {
        return "Ray{" +
                "pt=" + pt +
                ", direction=" + direction +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ray)) return false;
        Ray ray = (Ray) o;
        return this.pt.equals(ray.pt) &&
                this.direction.equals(ray.direction);
    }


}
