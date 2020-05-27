package elements;

import primitives.*;

public class DirectionalLight extends Light implements LightSource {
    private Vector _direction;

    /**
     * the elements.DirectionalLight Constructor receiving intensity of the light and it's direction
     * @param intensity of type Color
     * @param direction of type Vector
     */
    public DirectionalLight(Color intensity, Vector direction){
        super(intensity);
        _direction=direction.normalized();
    }

    @Override
    public Color getIntensity(Point3D p) {
        return super.getIntensity();
    }

    @Override
    public Vector getL(Point3D p) {
        return _direction;
    }
}
