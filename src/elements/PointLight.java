package elements;

import geometries.Sphere;
import primitives.*;

/**
 * the class PointLight is one kind of lightSource. It represents lights like light bulbs of room, that lighting in all directions
 * class point light implements light source
 * this class includes a shape lightning, we decide that this shape will be a bulb like a sphere
 *
 * @author mickael balensi
 */
public class PointLight extends Light implements LightSource {
    protected double kC, kL, kQ;
    protected Sphere bulb;
    protected static final int RADIUS=5;


    /**
     * constructor that receive all the parameters :
     *
     * @param intensity , the intensity of light
     * @param position the position of the central point of the light
     * @param kC
     * @param kL
     * @param kQ
     */
    public PointLight(Color intensity, Point3D position, double kC, double kL, double kQ, int radius) {
        super(intensity);
        bulb = new Sphere(position,radius);
/*        this.position=position;
        this.radius=radius;*/
        this.kC = kC;
        this.kL = kL;
        this.kQ = kQ;
    }
    public PointLight(Color intensity, Point3D position, double kC, double kL, double kQ) {
        this(intensity,position,kC,kL,kQ,RADIUS);
    }
    public PointLight(Color colorIntensity, Point3D position) {
        this(colorIntensity, position, 1d, 0d, 0d,RADIUS);
    }

    @Override
    public Color getIntensity(Point3D p) {
        double dSquared = p.distanceSquared(bulb.getCenter());
        double d = Math.sqrt(dSquared);
        return _intensity.reduce(kC + kL * d + kQ * dSquared);
    }

    @Override
    public Vector getL(Point3D p) {
        return p.subtract(bulb.getCenter()).normalize();
    }

    @Override
    public double getDistance(Point3D point) {
        return bulb.getCenter().distance(point);
    }

    @Override
    public Sphere getBulb(){
        return bulb;
    }

}
