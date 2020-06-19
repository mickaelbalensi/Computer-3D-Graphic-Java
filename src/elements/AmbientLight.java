package elements;

import primitives.*;

/**
 * This Class represents the ambient light in the scene that will help us to represent the 3D forms
 * @author mickael balensi
 */
public class AmbientLight extends Light {
    /**
     * elements.AmbientLight constructor receiving the color and the intensity of the light
     * @param Ia by type Color original full intensity
     * @param Ka by type double atenutation factor
     */
    public AmbientLight(Color Ia, double Ka) {
        super(new Color(Ia).scale(Ka));
    }
}
