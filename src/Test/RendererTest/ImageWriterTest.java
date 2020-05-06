package RendererTest;

import org.junit.Test;
import renderer.ImageWriter;

import java.awt.*;

/**
 * class testing of the class ImageWriter
 */
public class ImageWriterTest {

    @Test
    public void ImageTest() {
        ImageWriter myImage1 = new ImageWriter("myImage1", 10, 16, 1000, 1600);
        {
           for (int h=0;h<1600;h++)
           {
               for (int w=0;w<1000;w++) {
                   if (h % 100 != 0 && w % 100 != 0)
                       myImage1.writePixel(w, h, java.awt.Color.BLACK);
                   else
                       myImage1.writePixel(w, h, Color.WHITE);
                   }
               }
            }
            myImage1.writeToImage();
        }
    }





