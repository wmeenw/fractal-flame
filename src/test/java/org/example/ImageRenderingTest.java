package org.example;

import org.example.variations.*;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.util.List;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

public class ImageRenderingTest {

    @Test
    void generateImageShouldCreateFile() throws Exception {
        int width = 100;
        int height = 100;
        int iterations = 10000;
        List<AffineParams> affineParams = List.of(
                new AffineParams(0.5, 0.0, 0.0, 0.0, 0.5, 0.0)
        );
        List<WeightedVariation> variations = List.of(
                new WeightedVariation(new LinearVariation(), 1.0)
        );
        Random random = new Random(42);
        FractalFlameGenerator generator = new FractalFlameGenerator(width, height, iterations,
                affineParams, variations, random);
        int[][] histogram = generator.generate();
        assertNotNull(histogram);

        boolean nonZero = false;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (histogram[y][x] > 0) nonZero = true;
            }
        }
        assertTrue(nonZero, "Histogram should have at least one non-zero pixel");

        String outputPath = "test_render.png";
        ImageRenderer.render(histogram, width, height, outputPath);
        File file = new File(outputPath);
        assertTrue(file.exists());
        assertTrue(file.length() > 0);
        file.delete();
    }
}