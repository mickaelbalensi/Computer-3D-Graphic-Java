package primitives;

import java.io.DataInput;
import java.util.Objects;
import static primitives.Util.isZero;

public class Ray {
    protected Point3D pt;
    protected Vector direction;

    /**
     *
     * @param pt
     * @param vec
     */
    public Ray(Point3D pt, Vector vec) {
        this.pt=pt;
        direction=vec.normalize();
    }

    /**
     *
     * @param r
     */
    public  Ray(Ray r){
        this.pt=r.pt;
        this.direction=r.direction;
    }

    /**
     *
     * @return Point3D
     */
    public Point3D getPt() {
        return pt;
    }

    /**
     *
     * @return Vector
     */
    public Vector getDirection() {
        return direction;
    }

    /**
     *
     * @param lenght
     * @return Point
     * the point we reached after we added the vector(direction) to the initial point
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
