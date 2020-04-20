package primitives;

import java.io.DataInput;
import java.util.Objects;

public class Ray {
    protected Point3D pt;
    protected Vector direction;

    public Ray(Point3D pt, Vector vec) {
        this.pt=pt;
        direction=vec.normalize();
    }

    public  Ray(Ray r){
        this.pt=r.pt;
        this.direction=r.direction;
    }

    public Point3D getPt() {
        return pt;
    }

    public Vector getDirection() {
        return direction;
    }

    public Point3D getPoint(double t){
        return new Point3D(this.direction.scale(t).add(new Vector(this.pt)).getPt());
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
