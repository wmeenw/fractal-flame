package org.example;

import org.junit.jupiter.api.Test;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;

public class ImageRendererTest {

    @Test
    void renderCreatesPngFile() throws Exception {
        int width = 10, height = 10;
        int[][] histogram = new int[height][width];
        histogram[5][5] = 100;
        String path = "test_render.png";
        ImageRenderer.render(histogram, width, height, path);
        File f = new File(path);
        assertTrue(f.exists());
        assertTrue(f.length() > 0);
        f.delete();
    }

    @Test
    void renderHandlesZeroMaxCount() throws Exception {
        int[][] histogram = new int[10][10];
        String path = "test_zero.png";
        assertDoesNotThrow(() -> ImageRenderer.render(histogram, 10, 10, path));
        File f = new File(path);
        assertTrue(f.exists());
        f.delete();
    }
}