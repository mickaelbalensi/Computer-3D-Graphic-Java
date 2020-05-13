package RendererTest;

import org.junit.Test;
import primitives.Point3D;
import renderer.ImageWriter;

import java.awt.*;

/**
 * class testing of the class ImageWriter
 */
public class ImageWriterTest {
    /**
     * Test method for {@link renderer.ImageWriter#Image()}.
     */
    @Test
    public void ImageTest() {
        ImageWriter myImage = new ImageWriter("myImage2", 16, 10, 1600, 1000);
        {
           for (int h=0;h<1000;h++)
           {
               for (int w=0;w<1600;w++) {
                   if (h % 100 != 0 && w % 100 != 0)
                       myImage.writePixel(w, h, java.awt.Color.BLACK);
                   else
                       myImage.writePixel(w, h, Color.WHITE);
                   }
               }
            }
            myImage.writeToImage();
        }
    }





