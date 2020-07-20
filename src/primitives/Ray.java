package primitives;

import java.io.DataInput;
import java.util.Objects;
import static primitives.Util.isZero;

/**
 * ray leaving the camera
 */
public class Ray {
    protected Point3D pt;
    protected Vector direction;

    /**
     * Ctor
     * @param pt the beginning point of our vector
     * @param vec the direction of the vector going from the point
     */
    public Ray(Point3D pt, Vector vec) {
        this.pt=pt;
        direction=vec.normalize();
    }

    /**
     * Copy Ctor
     * @param r now our ray leaves from the point and is directed by our vector
     */
    public  Ray(Ray r){
        this.pt=r.pt;
        this.direction=r.direction;
    }

    /**
     * getter Point
     * @return Point3D whcih is our beggining point
     */
    public Point3D getPt() {
        return pt;
    }

    /**
     * getter Direction
     * @return Vector which is our direction
     */
    public Vector getDirection() {
        return direction;
    }

    /**
     * Calculate the endPoint of the ray
     * @param lenght which is the ray's lenght
     * @return Point which is the destination point reached by our vector
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
