package elements;

import primitives.*;

/**
 * This Class represents the light
 */
public class AmbientLight {
    Color intensity;

    public AmbientLight(Color Ia, double Ka){
        this.intensity=new Color(Ia).scale(Ka);


    }

    public Color GetIntensity(){
        return this.intensity;
    }
}
