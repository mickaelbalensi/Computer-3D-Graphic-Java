package elements;

import primitives.*;

/**
 * class directional light implement the interface light source.
 * It represent a light source like the sun, it lighting all geometries in the same lightning
 * This class have a field vector direction and its the direction of the light in the scene
 *
 * @author mickael balensi
 */
public class DirectionalLight extends Light implements LightSource {
    private Vector _direction; //direction of lightning

    /**
     * the elements.DirectionalLight Constructor receiving intensity of the light and it's direction
     *
     * @param intensity the Color of the light
     * @param direction the Vector direction of the light
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        _direction = direction.normalized();
    }

    @Override
    public Color getIntensity(Point3D p) {
        return super.getIntensity();
    }

    @Override
    public Vector getL(Point3D p) {
        return _direction;
    }

    @Override
    public double getDistance(Point3D point) {
        return Double.POSITIVE_INFINITY;
    }


}
