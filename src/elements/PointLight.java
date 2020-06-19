package elements;

import primitives.*;

/**
 * the class PointLight is one kind of lightSource. It represents lights like light bulbs of room, that lighting in all directions
 * class point light implements light source
 * this class includes the position of the central point of the light
 *
 * @author mickael balensi
 */
public class PointLight extends Light implements LightSource {
    protected Point3D _position;
    double _kC, _kL, _kQ;

    /**
     * constructor that receive all the parameters :
     *
     * @param intensity , the intensity of light
     * @param position the position of the central point of the light
     * @param kC
     * @param kL
     * @param kQ
     */
    public PointLight(Color intensity, Point3D position, double kC, double kL, double kQ) {
        super(intensity);
        _position = position;
        _kC = kC;
        _kL = kL;
        _kQ = kQ;
    }

    public PointLight(Color colorIntensity, Point3D position) {
        this(colorIntensity, position, 1d, 0d, 0d);
    }

    @Override
    public Color getIntensity(Point3D p) {
        double dSquared = p.distanceSquared(_position);
        double d = Math.sqrt(dSquared);
        return _intensity.reduce(_kC + _kL * d + _kQ * dSquared);
    }

    @Override
    public Vector getL(Point3D p) {
        return p.subtract(_position).normalize();
    }

    @Override
    public double getDistance(Point3D point) {
        return _position.distance(point);
    }
}
