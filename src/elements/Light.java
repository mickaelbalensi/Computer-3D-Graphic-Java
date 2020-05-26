package elements;

import primitives.*;

/**
 * this class includes all light source of the scene
 */
public abstract class Light {
    protected Color _intensity;

    /**
     * The elements.Light Constructor
     * @param intensity of type Color
     */
    public Light(Color intensity){
        this._intensity=intensity;
    }

    /**
     * the getter of intensity
     * @return the intensity of the light
     */
    public Color getIntensity(){
        return _intensity;
    }

}
