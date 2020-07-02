package elementTest;

import image.*;
import image.Eifel.*;
import org.junit.Test;

import elements.*;
import geometries.*;
import primitives.*;
import renderer.*;
import scene.Scene;

import java.util.ArrayList;

/**
 * Testing basic shadows
 *
 * @author Dan
 */
public class ShadowTests {

    /**
     * Produce a picture of a sphere and triangle with point light and shade
     */
    @Test
    public void SphereTriangleInitial() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));


        scene.addGeometries(
                new Sphere(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30), 60, new Point3D(0, 0, 200)),
                new Triangle(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30, 0.5, 0), //
                        new Point3D(-70, 40, 0), new Point3D(-40, 70, 0), new Point3D(-68, 68, 4)));

        scene.addLights(
                new SpotLight(new Color(400, 240, 0), //
                        new Point3D(-100, 100, -200), new Vector(1, -1, 3), 1, 1E-5, 1.5E-7));

        //ImageWriter imageWriter = new ImageWriter("sphereTriangleInitial", 100, 100, 10, 10);
        ImageWriter imageWriter = new ImageWriter("sphereTriangleInitial", 200, 200, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Sphere-Triangle shading - move triangle up-right
     */
    @Test
    public void SphereTriangleMove1() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

        scene.addGeometries(new Sphere(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30), //
                        60, new Point3D(0, 0, 200)), //
                new Triangle(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30), //
                        new Point3D(-50, 25, 0), new Point3D(-20, 55, 0), new Point3D(-48, 53, 4)));

        Color spotColor = new Color(400, 240, 0);
        Point3D spotPosition = new Point3D(-100, 100, -200);
        Vector spotDirection = new Vector(1, -1, 3);
        double kc = 1, kl = 1E-5, kq = 1.5E-7;
        SpotLight spot = new SpotLight(spotColor, spotPosition, spotDirection, kc, kl, kq);
        scene.addLights(spot);

        ImageWriter imageWriter = new ImageWriter("sphereTriangleMove1", 200, 200, 400, 400);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Sphere-Triangle shading - move triangle upper-righter
     */
    @Test
    public void SphereTriangleMove2() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

        scene.addGeometries(new Sphere(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30), //
                        60, new Point3D(0, 0, 200)), //
                new Triangle(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30), //
                        new Point3D(-40, 20, 0), new Point3D(-10, 50, 0), new Point3D(-38, 48, 4)));

        scene.addLights(new SpotLight(new Color(400, 240, 0), //
                new Point3D(-100, 100, -200), new Vector(1, -1, 3), 1, 1E-5, 1.5E-7));

        ImageWriter imageWriter = new ImageWriter("sphereTriangleMove2", 200, 200, 400, 400);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Sphere-Triangle shading - move spot closer
     */
    @Test
    public void SphereTriangleSpot1() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

        scene.addGeometries(new Sphere(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30), //
                        60, new Point3D(0, 0, 200)), //
                new Triangle(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30), //
                        new Point3D(-70, 40, 0), new Point3D(-40, 70, 0), new Point3D(-68, 68, 4)));

        scene.addLights(new SpotLight(new Color(400, 240, 0), //
                new Point3D(-100, 100, -150), new Vector(1, -1, 3), 1, 1E-5, 1.5E-7));

        ImageWriter imageWriter = new ImageWriter("sphereTriangleSpot1", 200, 200, 400, 400);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Sphere-Triangle shading - move spot even more close
     */
    @Test
    public void SphereTriangleSpot2() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

        scene.addGeometries(new Sphere(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30), //
                        60, new Point3D(0, 0, 200)), //
                new Triangle(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30), //
                        new Point3D(-70, 40, 0), new Point3D(-40, 70, 0), new Point3D(-68, 68, 4)));

        scene.addLights(new SpotLight(new Color(400, 240, 0), //
                new Point3D(-100, 100, -120), new Vector(1, -1, 3), 1, 1E-5, 1.5E-7));

        ImageWriter imageWriter = new ImageWriter("sphereTriangleSpot2", 200, 200, 400, 400);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a spot light with a Sphere producing a shading
     */
    @Test
    public void trianglesSphere() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.addGeometries( //
                new Triangle(Color.BLACK, new Material(0, 0.8, 60), //
                        new Point3D(-150, 150, 115), new Point3D(150, 150, 135), new Point3D(75, -75, 150)), //
                new Triangle(Color.BLACK, new Material(0, 0.8, 60), //
                        new Point3D(-150, 150, 115), new Point3D(-70, -70, 140), new Point3D(75, -75, 150)), //
                new Sphere(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30), // )
                        30, new Point3D(0, 0, 115)));

        scene.addLights(new SpotLight(new Color(700, 400, 400), //
                new Point3D(40, -40, -115), new Vector(-1, 1, 4), 1, 4E-4, 2E-5));

        ImageWriter imageWriter = new ImageWriter("trianglesSphere", 200, 200, 600, 600);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    @Test
    public void ombreTest() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

        scene.addGeometries(new Sphere(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30), //
                        30, new Point3D(-50, 0, 100)),
                new Plane(new Point3D(0, -100, 150), new Point3D(0, 100, 150), new Point3D(0, -70, 50), new Color(174, 209, 210)),
				/*new Sphere(new Color(java.awt.Color.RED), new Material(0.5, 0.5, 30), //
						5, new Point3D(0, -75, 100)),*/
                new Triangle(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30, 1, 0), //
                        new Point3D(-50, 25, 0), new Point3D(-20, 55, 0), new Point3D(-48, 53, 4)));

        scene.addLights(
                new PointLight(new Color(400, 240, 0), new Point3D(-100, 100, -200), 1, 1E-5, 1.5E-7));

        ImageWriter imageWriter = new ImageWriter("ombre", 200, 200, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();

    }

    @Test
    public void wolfTest() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(500, 500, -4000), new Vector(0, 0, 1), new Vector(0, 1, 0)));
        scene.setDistance(200);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0));

        scene.addGeometries(
                new Wolf1(new Color(java.awt.Color.darkGray), new Material(0.5, 0.5, 30), 2, new Point3D(0, 0, 0)).getList(),
                new Wolf2(new Color(java.awt.Color.white), new Material(0.5, 0.5, 30), 2, new Point3D(0, 0, 0)).getList(),
                new Wolf3(new Color(java.awt.Color.darkGray), new Material(0.5, 0.5, 30), 2, new Point3D(0, 0, 0)).getList(),
                new Wolf4(new Color(java.awt.Color.lightGray), new Material(0.5, 0.5, 30), 2, new Point3D(0, 0, 0)).getList()
        );

        scene.addLights(
                new PointLight(new Color(400, 240, 0), new Point3D(500, 500, -2000), 1, 1E-5, 1.5E-7));

        ImageWriter imageWriter = new ImageWriter("wolfTest", 200, 200, 500, 500);
        Render render = new Render(imageWriter, scene).setMultithreading(20);

        render.renderImage();
        render.writeToImage();

    }

    @Test
    public void ratTest() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(-13, 30, -4000), new Vector(0, 0, 1), new Vector(0, 1, 0)));
        scene.setDistance(400);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0));

        scene.addGeometries(
                new Rat0(new Color(java.awt.Color.darkGray), new Material(0.5, 0.5, 30), 2, new Point3D(0, 0, 0)).getList(),
                new Rat1(new Color(java.awt.Color.gray), new Material(0.5, 0.5, 30), 2, new Point3D(0, 0, 0)).getList(),
                new Rat2(new Color(java.awt.Color.white), new Material(0.5, 0.5, 30), 2, new Point3D(0, 0, 0)).getList()
        );

        scene.addLights(
                new PointLight(new Color(400, 240, 0), new Point3D(500, 500, -2000), 1, 1E-5, 1.5E-7));

        ImageWriter imageWriter = new ImageWriter("ratTest", 100, 100, 250, 250);
        Render render = new Render(imageWriter, scene).setMultithreading(20);

        render.renderImage();
        render.writeToImage();

    }

    @Test
    public void eifelTest() {
        Scene scene = new Scene("Test scene");
        //scene.setCamera(new Camera(new Point3D(50, 500, -3000), new Vector(0, 0, 1), new Vector(0, 1, 0)));
        scene.setCamera(new Camera(new Point3D(1.51345, 190, -3000), new Vector(0, 0, 1), new Vector(0, 1, 0)));
        scene.setDistance(300);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0));

        scene.addGeometries(
                new Eifel0(new Color(java.awt.Color.gray), new Material(0.5, 0.5, 30), 2, new Point3D(0, 0, 0)).getList(),
                new Eifel1(new Color(java.awt.Color.lightGray), new Material(0.5, 0.5, 30), 2, new Point3D(0, 0, 0)).getList(),
                new Eifel2(new Color(java.awt.Color.gray), new Material(0.5, 0.5, 30), 2, new Point3D(0, 0, 0)).getList(),
                new Eifel3(new Color(java.awt.Color.lightGray), new Material(0.5, 0.5, 30), 2, new Point3D(0, 0, 0)).getList(),
                new Eifel4(new Color(java.awt.Color.gray), new Material(0.5, 0.5, 30), 2, new Point3D(0, 0, 0)).getList(),
                new Eifel5(new Color(java.awt.Color.lightGray), new Material(0.5, 0.5, 30), 2, new Point3D(0, 0, 0)).getList(),
                new Eifel6(new Color(java.awt.Color.gray), new Material(0.5, 0.5, 30), 2, new Point3D(0, 0, 0)).getList(),
                new Eifel8(new Color(java.awt.Color.gray), new Material(0.5, 0.5, 30), 2, new Point3D(0, 0, 0)).getList(),
                new Eifel10(new Color(java.awt.Color.gray), new Material(0.5, 0.5, 30), 2, new Point3D(0, 0, 0)).getList(),
                new Eifel11(new Color(java.awt.Color.lightGray), new Material(0.5, 0.5, 30), 2, new Point3D(0, 0, 0)).getList(),
                new Eifel13(new Color(java.awt.Color.gray), new Material(0.5, 0.5, 30), 2, new Point3D(0, 0, 0)).getList(),
                new Eifel14(new Color(java.awt.Color.lightGray), new Material(0.5, 0.5, 30), 2, new Point3D(0, 0, 0)).getList(),
                new Eifel15(new Color(java.awt.Color.gray), new Material(0.5, 0.5, 30), 2, new Point3D(0, 0, 0)).getList(),
                new Eifel16(new Color(java.awt.Color.lightGray), new Material(0.5, 0.5, 30), 2, new Point3D(0, 0, 0)).getList(),
                new Eifel17(new Color(java.awt.Color.gray), new Material(0.5, 0.5, 30), 2, new Point3D(0, 0, 0)).getList(),
                new Eifel18(new Color(java.awt.Color.lightGray), new Material(0.5, 0.5, 30), 2, new Point3D(0, 0, 0)).getList(),
                new Eifel19(new Color(java.awt.Color.gray), new Material(0.5, 0.5, 30), 2, new Point3D(0, 0, 0)).getList(),
                new Eifel20(new Color(java.awt.Color.lightGray), new Material(0.5, 0.5, 30), 2, new Point3D(0, 0, 0)).getList(),
                new Eifel21(new Color(java.awt.Color.gray), new Material(0.5, 0.5, 30), 2, new Point3D(0, 0, 0)).getList(),
                new Eifel22(new Color(java.awt.Color.lightGray), new Material(0.5, 0.5, 30), 2, new Point3D(0, 0, 0)).getList(),
                new Eifel23(new Color(java.awt.Color.gray), new Material(0.5, 0.5, 30), 2, new Point3D(0, 0, 0)).getList(),
                new Eifel24(new Color(java.awt.Color.lightGray), new Material(0.5, 0.5, 30), 2, new Point3D(0, 0, 0)).getList(),
                new Eifel25(new Color(java.awt.Color.gray), new Material(0.5, 0.5, 30), 2, new Point3D(0, 0, 0)).getList(),
                new Eifel26(new Color(java.awt.Color.lightGray), new Material(0.5, 0.5, 30), 2, new Point3D(0, 0, 0)).getList(),
                new Eifel27(new Color(java.awt.Color.gray), new Material(0.5, 0.5, 30), 2, new Point3D(0, 0, 0)).getList()
        );

        scene.addLights(
                new PointLight(new Color(400, 240, 0), new Point3D(150, 700, -0), 1, 1E-5, 1.5E-7));

        ImageWriter imageWriter = new ImageWriter("eifelFinal4Test", 100, 100, 250, 250);
        Render render = new Render(imageWriter, scene).setMultithreading(20);

        render.renderImage();
        render.writeToImage();

    }

    @Test
    public void eifelTailleTest() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(70, 400, -3000), new Vector(0, 0, 1), new Vector(0, 1, 0)));
        scene.setDistance(400);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0));

        scene.addGeometries(
		/*		new Eifel0(new Color(java.awt.Color.red), new Material(0.5, 0.5, 30),2,new Point3D(0,0,0)).getList(),
				new Eifel1(new Color(java.awt.Color.orange), new Material(0.5, 0.5, 30),2,new Point3D(0,0,0)).getList(),
				new Eifel2(new Color(java.awt.Color.red), new Material(0.5, 0.5, 30),2,new Point3D(0,0,0)).getList(),
				new Eifel3(new Color(java.awt.Color.orange), new Material(0.5, 0.5, 30),2,new Point3D(0,0,0)).getList(),
				new Eifel4(new Color(java.awt.Color.red), new Material(0.5, 0.5, 30),2,new Point3D(0,0,0)).getList(),
				new Eifel5(new Color(java.awt.Color.orange), new Material(0.5, 0.5, 30),2,new Point3D(0,0,0)).getList(),
				new Eifel6(new Color(java.awt.Color.red), new Material(0.5, 0.5, 30),2,new Point3D(0,0,0)).getList(),
				new Eifel7(new Color(java.awt.Color.orange), new Material(0.5, 0.5, 30),2,new Point3D(0,0,0)).getList(),
				new Eifel8(new Color(java.awt.Color.red), new Material(0.5, 0.5, 30),2,new Point3D(0,0,0)).getList(),
				new Eifel9(new Color(java.awt.Color.orange), new Material(0.5, 0.5, 30),2,new Point3D(0,0,0)).getList(),
				new Eifel10(new Color(java.awt.Color.red), new Material(0.5, 0.5, 30),2,new Point3D(0,0,0)).getList(),
				new Eifel11(new Color(java.awt.Color.orange), new Material(0.5, 0.5, 30),2,new Point3D(0,0,0)).getList(),
				new Eifel13(new Color(java.awt.Color.red), new Material(0.5, 0.5, 30),2,new Point3D(0,0,0)).getList(),
				new Eifel14(new Color(java.awt.Color.orange), new Material(0.5, 0.5, 30),2,new Point3D(0,0,0)).getList(),
				new Eifel15(new Color(java.awt.Color.red), new Material(0.5, 0.5, 30),2,new Point3D(0,0,0)).getList(),
				new Eifel16(new Color(java.awt.Color.orange), new Material(0.5, 0.5, 30),2,new Point3D(0,0,0)).getList(),
				new Eifel17(new Color(java.awt.Color.red), new Material(0.5, 0.5, 30),2,new Point3D(0,0,0)).getList(),
				new Eifel18(new Color(java.awt.Color.orange), new Material(0.5, 0.5, 30),2,new Point3D(0,0,0)).getList(),
				new Eifel19(new Color(java.awt.Color.red), new Material(0.5, 0.5, 30),2,new Point3D(0,0,0)).getList(),
				new Eifel20(new Color(java.awt.Color.orange), new Material(0.5, 0.5, 30),2,new Point3D(0,0,0)).getList(),
				new Eifel21(new Color(java.awt.Color.red), new Material(0.5, 0.5, 30),2,new Point3D(0,0,0)).getList(),
				new Eifel22(new Color(java.awt.Color.orange), new Material(0.5, 0.5, 30),2,new Point3D(0,0,0)).getList(),
				new Eifel23(new Color(java.awt.Color.red), new Material(0.5, 0.5, 30),2,new Point3D(0,0,0)).getList(),
				new Eifel24(new Color(java.awt.Color.orange), new Material(0.5, 0.5, 30),2,new Point3D(0,0,0)).getList(),
				new Eifel25(new Color(java.awt.Color.red), new Material(0.5, 0.5, 30),2,new Point3D(0,0,0)).getList(),
				new Eifel26(new Color(java.awt.Color.orange), new Material(0.5, 0.5, 30),2,new Point3D(0,0,0)).getList(),
				*/
                new Eifel27(new Color(java.awt.Color.red), new Material(0.5, 0.5, 30), 2, new Point3D(0, 0, 0)).getList()
        );

        scene.addLights(
                new PointLight(new Color(400, 240, 0), new Point3D(500, 500, -2000), 1, 1E-5, 1.5E-7));

        ImageWriter imageWriter = new ImageWriter("eifelFinal2Test", 100, 200, 250, 500);
        Render render = new Render(imageWriter, scene).setMultithreading(20);

        render.renderImage();
        render.writeToImage();

    }

    @Test
    public void catTest() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(200, 200, -4000), new Vector(0, 0, 1), new Vector(0, 1, 0)));
        scene.setDistance(200);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0));

        //scene.addBox(new Box(new Cat()));
        /*scene.addGeometries(

                new Cat1(new Color(java.awt.Color.darkGray), new Material(0.5, 0.5, 30), .5, new Point3D(0, 0, 0)).getList(),
                new Cat2(new Color(java.awt.Color.gray), new Material(0.5, 0.5, 30), .5, new Point3D(0, 0, 0)).getList(),
                new Cat3(new Color(java.awt.Color.white), new Material(0.5, 0.5, 30), .5, new Point3D(0, 0, 0)).getList(),
                new Cat4(new Color(java.awt.Color.darkGray), new Material(0.5, 0.5, 30), .5, new Point3D(0, 0, 0)).getList(),
                new Cat5(new Color(java.awt.Color.gray), new Material(0.5, 0.5, 30), .5, new Point3D(0, 0, 0)).getList(),
                new Cat6(new Color(java.awt.Color.darkGray), new Material(0.5, 0.5, 30), .5, new Point3D(0, 0, 0)).getList()

        );*/
        scene.addGeometries(
                new Plane( new Vector(0,0,-1),new Point3D(0, 0, 600), new Color(89,114,210), new Material(0.5, 0.5, 60, .8, 0))
                //new Plane( new Vector(0, -1, 0), new Point3D(0, 0, 0),new Color(191,148,108), new Material(0.5, 0.5, 60, .5, 1))
                //new Plane( new Point3D(30, -150, 115), new Point3D(-30, -150, 135), new Point3D(-15, 75, 150),new Color(106,193,187), new Material(0.5, 0.5, 60, .5, 1))
                //new Triangle(Color.BLACK, new Material(0.5, 0.5, 60, 0, 1), new Point3D(-150, -150, 115), new Point3D(150, -150, 135), new Point3D(75, 75, 150)), //
                //new Triangle(Color.BLACK, new Material(0.5, 0.5, 60, 0, 1), new Point3D(-150, -150, 115), new Point3D(-70, 70, 140), new Point3D(75, 75, 150))
        );

        scene.addLights(
                new PointLight(new Color(400, 240, 120), new Point3D(-100, 100, -500), 1, 1E-5, 1.5E-7));

        ImageWriter imageWriter =
                new ImageWriter("cat3planTest", 80, 40, 1000, 500);
                 //  new ImageWriter("cat2planTest", 20, 10, 250, 125);
        Render render = new Render(imageWriter, scene).setMultithreading(20);

        render.renderImage();
        render.writeToImage();

    }

    @Test
    public void testPositionCamera() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(-1000, 0, 200), new Vector(1, 0, 0), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

        scene.addGeometries(
                new Sphere(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30), 60, new Point3D(0, 0, 200)),
                new Sphere(new Point3D(-100, -100, 200), 10, new Color(java.awt.Color.BLUE)),
                new Sphere(new Point3D(-100, 100, 200), 10, new Color(java.awt.Color.GREEN)),
                new Sphere(new Point3D(100, -100, 200), 10, new Color(java.awt.Color.red)),
                new Sphere(new Point3D(100, 100, 200), 10, new Color(java.awt.Color.white)),
                new Sphere(new Point3D(-50, 0, 200), 10, new Color(java.awt.Color.black)),

                new Triangle(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30, 0.5, 0), //
                        new Point3D(-70, 40, 0), new Point3D(-40, 70, 0), new Point3D(-68, 68, 4)));

        scene.addLights(
                new SpotLight(new Color(400, 240, 0), //
                        new Point3D(-100, 100, -200), new Vector(1, -1, 3), 1, 1E-5, 1.5E-7));

        //ImageWriter imageWriter = new ImageWriter("sphereTriangleInitial", 100, 100, 10, 10);
        ImageWriter imageWriter = new ImageWriter("positionCamera2", 200, 200, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    @Test
    public void tailleTest() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

        scene.addGeometries(
                new Sphere(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30), 60, new Point3D(0, 0, 200)),
                new Triangle(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30, 0.5, 0), //
                        new Point3D(-70, 40, 0), new Point3D(-40, 70, 0), new Point3D(-68, 68, 4)));

        scene.addLights(
                new SpotLight(new Color(400, 240, 0), //
                        new Point3D(-100, 100, -200), new Vector(1, -1, 3), 1, 1E-5, 1.5E-7));

        //ImageWriter imageWriter = new ImageWriter("sphereTriangleInitial", 100, 100, 10, 10);
        ImageWriter imageWriter = new ImageWriter("sphereTriangleInitial", 200, 200, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    @Test
    public void ConstrucMyImage() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

        scene.addGeometries(
                new Sphere(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30, 0, 0.5), 20, new Point3D(-20, 70, 0)),
                new Sphere(new Color(java.awt.Color.RED), new Material(1, 1, 30, 0.6, 0), 20, new Point3D(-20, -30, 0)),
                new Sphere(new Color(java.awt.Color.BLACK), new Material(0.25, 0.25, 30, 0.6, 1), 20, new Point3D(30, 20, 0)),
                new Sphere(new Color(java.awt.Color.ORANGE), new Material(0.5, 0.5, 10, 0.6, 0), 20, new Point3D(-70, 20, 0)),
                new Sphere(new Color(java.awt.Color.GREEN), new Material(0.5, 0.5, 10, 0, 0.5), 30, new Point3D(-20, 20, 0)),
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60, 0, 1), new Point3D(-150, 150, 115), new Point3D(150, 150, 135), new Point3D(75, -75, 150)), //
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60, 0, 1), new Point3D(-150, 150, 115), new Point3D(-70, -70, 140), new Point3D(75, -75, 150))
        );


        scene.addLights(
                new SpotLight(new Color(400, 240, 0), //
                        new Point3D(-100, 10, -200), new Vector(1, -1, 3), 1, 1E-5, 1.5E-7)
        );

        ImageWriter imageWriter = new ImageWriter("taille", 200, 200, 400, 400);
        Render render = new Render(imageWriter, scene).setMultithreading(6);

        render.renderImage();
        render.writeToImage();


    }

    @Test
    public void BoxTest() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));


        scene.addGeometries(
                new Box (new Sphere(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30), 60, new Point3D(0, 0, 200))),
                new Box (new Triangle(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30, 0.5, 0), //
                        new Point3D(-70, 40, 0), new Point3D(-40, 70, 0), new Point3D(-68, 68, 4))));

        scene.addLights(
                new SpotLight(new Color(400, 240, 0), //
                        new Point3D(-100, 100, -200), new Vector(1, -1, 3), 1, 1E-5, 1.5E-7));

        //ImageWriter imageWriter = new ImageWriter("sphereTriangleInitial", 100, 100, 10, 10);
        ImageWriter imageWriter = new ImageWriter("sphereTriangleInitial", 200, 200, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }
}
