package primitives;

import geometries.Intersectable;

import static primitives.Util.isZero;
import geometries.Intersectable;

import java.util.ArrayList;
import java.util.Random;

public class Ray {
    protected Point3D pt;
    protected Vector direction;
    private static final double DELTA = 0.1;

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
     *
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
     *
     * @return Vector which is our direction
     */
    public Vector getDirection() {
        return direction;
    }

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

    /**
     * we can have several rays who get threw a pixel
     * @param center
     * @param radius
     * @return
     */
    public ArrayList<Ray> getListRays(Point3D center, int radius){
        ArrayList<Ray> listRay = new ArrayList<Ray>();
        listRay.add(this);

        //int radius = (int) lightSource.getBulb().getRadius();
        //Point3D center = lightSource.getBulb().getCenter();
        double centerX = center.getX().get();
        double centerY = center.getY().get();
        double centerZ = center.getZ().get();

        Random rand = new Random();
        for (int i = 0; i < 100; i++) {
            double x = rand.nextInt((int) centerX + radius - (int) (centerX - radius) + 1) + (centerX - radius);
            double y = rand.nextInt((int) centerY + radius - (int) (centerY - radius) + 1) + (centerY - radius);
            double z = rand.nextInt((int) centerZ + radius - (int) (centerZ - radius) + 1) + (centerZ - radius);

            Point3D pointOfSphere = new Point3D(x, y, z);
            Vector dest = pointOfSphere.subtract(this.pt);
            Ray r = new Ray(this.pt, dest);
            listRay.add(r);
        }
        return listRay;
    }

}
