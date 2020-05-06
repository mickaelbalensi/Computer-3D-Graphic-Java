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
        ImageWriter newImage = new ImageWriter("newImage", 1000, 1600, 1000, 1600);
        {
           for (int h=0;h<1600;h++)
           {
               for (int w=0;w<1000;w++) {
                   if (h % 10 != 0 && w % 10 != 0)
                       newImage.writePixel(w, h, java.awt.Color.BLACK);
                   else
                       newImage.writePixel(w, h, Color.WHITE);
                   }
               }
            }
            newImage.writeToImage();
        }
    }





