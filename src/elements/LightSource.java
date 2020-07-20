package elements;

import geometries.Sphere;
import primitives.*;

/**
 * The aim of the interface LightSource is to represent all kind of source of light
 * that lightning, whether it be the DirectionalLight, the PointLight or SpotLight
 * interface light source that all component of the light that is composed
 * by 3 functions
 * @author  gabriel and baryohai
 */
public interface LightSource {
    /**
     * this function calculates the intensity of the light at a lighted point using light propagation model
     * @param p the lighted point
     * @return intensity of light
     */
    Color getIntensity(Point3D p);

    /**
     * this function will calculated the vector of light that impact the geometry
     * @param p the lighted point
     * @return vector
     */
    Vector getL(Point3D p);

    /**
     * this function gets the Distance between the central Point of the light and a point
     * @param point point
     * @return the distance lightSource-Point
     */
    double getDistance(Point3D point);

    /**
     * After refactoring, we add that the lightSource is not only an illuminating point
     * it's also a geometry. So we add a bulb for PointLight abd SpotLight
     * @return the bulb
     */
    Sphere getBulb();
}
