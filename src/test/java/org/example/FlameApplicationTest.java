package org.example;

import org.junit.jupiter.api.Test;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;

public class FlameApplicationTest {

    @Test
    void runWithDefaultConfigGeneratesImage() throws Exception {
        String[] args = {};
        FlameApplication app = new FlameApplication();
        app.run(args);
        File output = new File("result.png");
        assertTrue(output.exists());
        assertTrue(output.length() > 0);
        output.delete();
    }

    @Test
    void applySymmetryRotatesPoints() {
        int width = 100, height = 100;
        int[][] hist = new int[height][width];
        hist[50][50] = 10;
        FlameApplication app = new FlameApplication();
        int[][] result = app.applySymmetry(hist, width, height, 4);
        int total = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                total += result[y][x];
            }
        }
        assertEquals(40, total);
    }
}