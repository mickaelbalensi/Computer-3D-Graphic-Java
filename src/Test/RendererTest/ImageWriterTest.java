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
        ImageWriter myImage2 = new ImageWriter("myImage2", 16, 10, 1600, 1000);
        {
           for (int h=0;h<1000;h++)
           {
               for (int w=0;w<1600;w++) {
                   if (h % 100 != 0 && w % 100 != 0)
                       myImage2.writePixel(w, h, java.awt.Color.BLACK);
                   else
                       myImage2.writePixel(w, h, Color.WHITE);
                   }
               }
            }
            myImage2.writeToImage();
        }
    }





