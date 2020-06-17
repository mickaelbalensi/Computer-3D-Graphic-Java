package elements;

import primitives.*;

/**
 * this class includes all light of the scene
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

    public Color getIntensity(){
        return _intensity;
    }

}
