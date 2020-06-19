package elements;

import primitives.*;

/**
 * the class SpotLight is one kind of lightSource. It represents lights like spot, that lighting just in the direction of th spot
 * this class includes its direction light
 *
 * @author mickael balensi
 */
public class SpotLight extends PointLight {
    private Vector direction;
    /**
     * constructor for the class that receive all the parameter and send them to the father class
     *
     * @param intensity the intensity of light
     * @param position the position of the central point of the light
     * @param kC
     * @param kL
     * @param kQ
     * @param direction the direction light
     */
    public SpotLight(Color intensity, Point3D position, Vector direction, double kC, double kL, double kQ) {
        this(intensity, position, kC, kL, kQ, direction);
    }

    public SpotLight(Color intensity, Point3D position, double kC, double kL, double kQ, Vector direction) {
        super(intensity, position, kC, kL, kQ);
        this.direction = direction.normalize();
    }

    @Override
    public Color getIntensity(Point3D p) {
        double projection = direction.dotProduct(getL(p));
        if (Util.alignZero(projection) <= 0) return Color.BLACK;

        return (super.getIntensity(p).scale(projection));
    }
}
