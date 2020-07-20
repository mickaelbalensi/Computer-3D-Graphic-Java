package geometriesTest;

import elements.AmbientLight;
import elements.Camera;
import elements.PointLight;
import elements.SpotLight;
import geometries.*;
import image.*;
import geometries.Polygon;
import org.junit.Test;

import primitives.*;
import primitives.Color;
import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;

import java.awt.*;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BoxTest {
    public static String nameFile;
    public static int count=0;
    private static boolean ACTIVATE = true;
    private static boolean DEACTIVATE = false;
    @Test
    public void boxTest() {

        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(200, 200, -4000), new Vector(0, 0, 1), new Vector(0, 1, 0)));
        scene.setDistance(200);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0));

        Geometries sphereGeo=new Geometries(new Sphere(new Point3D(5, 3, 7), 1));
        Geometries catGeo =new Cat().getGeometries();

        scene.addGroupGeometries(
                //catGeo,
                new Geometries(new Sphere(new Point3D(5, 3, 7), 1)),
                new Geometries(new Sphere(new Point3D(2, 3, 7), 1)),
                new Geometries(new Sphere(new Point3D(-1, 3, 7), 1))
        );

        scene.addLights(
                new PointLight(new Color(400, 240, 120), new Point3D(-100, 100, -500), 1, 1E-5, 1.5E-7));
        nameFile="boxTest"+count;
        ImageWriter imageWriter =
                new ImageWriter(nameFile, 80, 40, 1000, 500);
        //  new ImageWriter("cat2planTest", 20, 10, 250, 125);
        Render render = new Render(imageWriter, scene);

        render.renderImage(ACTIVATE);
        render.writeToImage();


    }


    @Test
    public void SphereTriangleInitial() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));


        scene.addGroupGeometries(
                new Geometries(new Sphere(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30), 60, new Point3D(0, 0, 200)))
               // new Triangle(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30, 0.5, 0), //
               //         new Point3D(-70, 40, 0), new Point3D(-40, 70, 0), new Point3D(-68, 68, 4)));
        );

        scene.addLights(
                new SpotLight(new Color(400, 240, 0), //
                        new Point3D(-100, 100, -200), new Vector(1, -1, 3), 1, 1E-5, 1.5E-7));

        //ImageWriter imageWriter = new ImageWriter("sphereTriangleInitial", 100, 100, 10, 10);
        ImageWriter imageWriter = new ImageWriter("box12", 200, 200, 500, 500);
        Render render = new Render(imageWriter, scene).setMultithreading(3);

        render.renderImage(ACTIVATE);
        render.writeToImage();
    }

    @Test
    public void catTest() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(200, 200, -4000), new Vector(0, 0, 1), new Vector(0, 1, 0)));
        scene.setDistance(200);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0));

        Geometries geo =new Geometries();

        geo.addList(new Cat1(new Color(java.awt.Color.darkGray), new Material(0.5, 0.5, 30), .5, new Point3D(0, 0, 0)).getList());
        scene.addGroupGeometries(
                new Geometries(new Cat().getGeometries())
        );

        scene.addGeometries(
                new Plane( new Vector(0,0,-1),new Point3D(0, 0, 600), new Color(89,114,210), new Material(0.5, 0.5, 60, .8, 0))
                //new Cat().getGeometries()
        );

        scene.addLights(
                new PointLight(new Color(400, 240, 120), new Point3D(-100, 100, -500), 1, 1E-5, 1.5E-7));

        ImageWriter imageWriter =
                new ImageWriter("cat3planTest2", 40, 40, 50, 50);
        //  new ImageWriter("cat2planTest", 20, 10, 250, 125);
        Render render = new Render(imageWriter, scene).setMultithreading(3);

        render.renderImage(ACTIVATE);
        render.writeToImage();

    }
    @Test
    public void ConstrucMyImage() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

        /**/scene.addGroupGeometries(
                new Geometries( new Sphere(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30, 0, 0.5), 20, new Point3D(-20, 70, 0))),
                new Geometries( new Sphere(new Color(java.awt.Color.RED), new Material(1, 1, 30, 0.6, 0), 20, new Point3D(-20, -30, 0))),
                new Geometries( new Sphere(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 30, 0.6, 1), 20, new Point3D(30, 20, 0))),
                new Geometries( new Sphere(new Color(java.awt.Color.ORANGE), new Material(0.5, 0.5, 10, 0.6, 0), 20, new Point3D(-70, 20, 0))),
                new Geometries( new Sphere(new Color(java.awt.Color.GREEN), new Material(0.5, 0.5, 10, 0, 0.5), 30, new Point3D(-20, 20, 0))),
                new Geometries( new Triangle(Color.BLACK, new Material(0.5, 0.5, 60, 0, 1), new Point3D(-150, 150, 115), new Point3D(150, 150, 135), new Point3D(75, -75, 150))), //
                new Geometries( new Triangle(Color.BLACK, new Material(0.5, 0.5, 60, 0, 1), new Point3D(-150, 150, 115), new Point3D(-70, -70, 140), new Point3D(75, -75, 150)))
        );/**/
        /*
        scene.addGeometries(
                new Sphere(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30, 0, 0.5), 20, new Point3D(-20, 70, 0)),
                new Sphere(new Color(java.awt.Color.RED), new Material(1, 1, 30, 0.6, 0), 20, new Point3D(-20, -30, 0)),
                new Sphere(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 30, 0.6, 1), 20, new Point3D(30, 20, 0)),
                new Sphere(new Color(java.awt.Color.ORANGE), new Material(0.5, 0.5, 10, 0.6, 0), 20, new Point3D(-70, 20, 0)),
                new Sphere(new Color(java.awt.Color.GREEN), new Material(0.5, 0.5, 10, 0, 0.5), 30, new Point3D(-20, 20, 0))
                //new Triangle(Color.BLACK, new Material(0.5, 0.5, 60, 0, 1), new Point3D(-150, 150, 115), new Point3D(150, 150, 135), new Point3D(75, -75, 150)), //
                //new Triangle(Color.BLACK, new Material(0.5, 0.5, 60, 0, 1), new Point3D(-150, 150, 115), new Point3D(-70, -70, 140), new Point3D(75, -75, 150))
        );*/



        scene.addLights(
                new SpotLight(new Color(400, 240, 0), //
                        new Point3D(-100, 10, -200), new Vector(1, -1, 3), 1, 1E-5, 1.5E-7)
        );

        ImageWriter imageWriter = new ImageWriter("boules en cross", 200, 200, 400, 400);
        Render render = new Render(imageWriter, scene).setMultithreading(3);

        render.renderImage(ACTIVATE);
        render.writeToImage();


    }
}
