package elementTest;

import org.junit.Test;

import elements.*;
import geometries.*;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Test rendering abasic image
 * 
 * @author Dan
 */
public class LightsTests {

    /**
     * Produce a picture of a sphere lighted by a directional light
     */
    @Test
    public void sphereDirectional() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -10000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(10000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

        scene.addGeometries(
                new Sphere(new Point3D(0, 0, 50),50,new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 100)));

        scene.addLights(new DirectionalLight(new Color(500, 300, 0), new Vector(1, -1, 1)));

        ImageWriter imageWriter = new ImageWriter("sphereDirectional", 150, 150, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a point light
     */
    @Test
    public void spherePoint() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

        scene.addGeometries(
                new Sphere(  new Point3D(0, 0, 50),50 ,new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 100)));

        scene.addLights(new PointLight(new Color(500, 300, 0), new Point3D(-50, 50, -50), 1, 0.00001, 0.000001));

        ImageWriter imageWriter = new ImageWriter("spherePoint1", 150, 150, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void sphereSpot() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

        scene.addGeometries(
                new Sphere( new Point3D(0, 0, 50),50,new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 100)));

        scene.addLights(new SpotLight(new Color(500, 300, 0), new Point3D(-50, 50, -50),
                 1, 0.00001, 0.00000001,new Vector(1, -1, 2)));

        ImageWriter imageWriter = new ImageWriter("sphereSpot2", 150, 150, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture with some spheres lighted by many light source : 2 spot and 1 Point Light
     */
    @Test
    public void sphereMultipleSource(){
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -10000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(10000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

        scene.addGeometries(
                new Sphere( new Point3D(-22, -22, 0),13,new Color(java.awt.Color.red), new Material(0.5, 0.5, 100)),
                new Sphere( new Point3D(-5, -5, 0),11,new Color(java.awt.Color.green), new Material(0.5, 0.5, 100)),
                new Sphere( new Point3D(10, 10, 0),9,new Color(java.awt.Color.CYAN), new Material(0.5, 0.5, 100)),
                new Sphere( new Point3D(22, 22, 0),7,new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 100)),
                new Sphere( new Point3D(30, 30, 0),4,new Color(java.awt.Color.BLACK), new Material(0.5, 0.5, 100))
        );

        scene.addLights(
                new SpotLight(new Color(300, 300, 300), new Point3D(-50, 50, -50),
                1, 0.00001, 0.00000001,new Vector(1, -1, 2)),
                new SpotLight(new Color(300, 300, 300), new Point3D(150, 50, -50),
                        1, 0.00001, 0.00000001,new Vector(-1, -1, 1)),
                new PointLight(new Color(100, 200, 300), new Point3D(150, 0, -50),
                        1, 0.00001, 0.00000001/*,new Vector(0, 1, 1)*/)

        );

        ImageWriter imageWriter = new ImageWriter("sphereTwoSpot", 150, 150, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();

    }
    /**
     * Produce a picture of a two triangles lighted by a directional light
     */
    @Test
    public void trianglesDirectional() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.addGeometries(
                new Triangle(new Point3D(-150, 150, 150), new Point3D(150, 150, 150),
                        new Point3D(75, -75, 150),Color.BLACK, new Material(0.8, 0.2, 300)),
                new Triangle(new Point3D(-150, 150, 150), new Point3D(-70, -70, 50),
                        new Point3D(75, -75, 150),Color.BLACK, new Material(0.8, 0.2, 300)));

        scene.addLights(new DirectionalLight(new Color(300, 150, 150), new Vector(0, 0, 1)));

        ImageWriter imageWriter = new ImageWriter("trianglesDirectional", 200, 200, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a point light
     */
    @Test
    public void trianglesPoint() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -100000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(100000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.addGeometries(
                new Triangle( new Point3D(-150, 150, 150), new Point3D(150, 150, 150),
                        new Point3D(75, -75, 150),Color.BLACK, new Material(0.5, 0.5, 300)),
                new Triangle(new Point3D(-150, 150, 150), new Point3D(-70, -70, 50),
                        new Point3D(75, -75, 150),Color.BLACK, new Material(0.5, 0.5, 300)));

        scene.addLights(new PointLight(new Color(500, 250, 250),
                new Point3D(10, 10, 130), 1, 0.0005, 0.0005));

        ImageWriter imageWriter = new ImageWriter("trianglesPoint", 200, 200, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a spot light
     */
    @Test
    public void trianglesSpot() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.addGeometries(
                new Triangle( new Point3D(-150, 150, 150), new Point3D(150, 150, 150), new Point3D(75, -75, 150),
                        Color.BLACK, new Material(0.5, 0.5, 300)),
                new Triangle( new Point3D(-150, 150, 150), new Point3D(-70, -70, 50), new Point3D(75, -75, 150),
                        Color.BLACK, new Material(0.5, 0.5, 300)));

        scene.addLights(new SpotLight(new Color(500, 250, 250), new Point3D(10, 10, 130),
                1, 0.0001, 0.000005, new Vector(-2, 2, 1)));

        ImageWriter imageWriter = new ImageWriter("trianglesSpot", 200, 200, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }


    
}
