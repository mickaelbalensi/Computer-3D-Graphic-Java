package elementTest;

import elements.AmbientLight;
import elements.Camera;
import elements.SpotLight;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;
import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;

public class ImageTestMiniProject1 {

    @Test
    void ConstrucMyImage()
    {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, 1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

        scene.addGeometries(new Sphere(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30), //
                        20, new Point3D(0, 50, 0)),new Sphere(new Color(java.awt.Color.BLUE), new Material(1, 1, 30), //
                20, new Point3D(0, -50, 0)),new Sphere(new Color(java.awt.Color.BLUE), new Material(0.25, 0.25, 30), //
                20, new Point3D(50, 0, 0)),new Sphere(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 10), //
                20, new Point3D(-50, 0, 0)));



        scene.addLights(new SpotLight(new Color(400, 240, 0), //
                new Point3D(-100, 100, -200), new Vector(1, -1, 3), 1, 1E-5, 1.5E-7));

        ImageWriter imageWriter = new ImageWriter("sphereTriangleInitial", 200, 200, 400, 400);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();

    }






}
