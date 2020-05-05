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
        ImageWriter newImage = new ImageWriter("newImage", 10, 16, 1000, 1600);
        int w = 0;
        int h = 0;
        {
            while ((w < 1000) || (h < 1600)) {
                if (h % 10 != 0 && w % 10 != 0)
                    newImage.writePixel(h, w, java.awt.Color.BLACK);
                else
                    newImage.writePixel(h, w, Color.WHITE);
                if (h == 1600) {h = 0;w++;}
                h++;
            }
        }
            newImage.writeToImage();

    }
}




