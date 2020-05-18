package elements;

import primitives.*;

/**
 * This Class represents the ambient light in the scene
 */
public class AmbientLight {
    Color intensity;

    /**
     * elements.AmbientLight constructor receiving the color and the intensity of the light
     * @param Ia by type Color
     * @param Ka by type double
     */
    public AmbientLight(Color Ia, double Ka){
        this.intensity=new Color(Ia).scale(Ka);
    }

    /**
     * getter of the Color intensity
     * @return
     */
    public Color getIntensity(){
        return this.intensity;
    }
}
