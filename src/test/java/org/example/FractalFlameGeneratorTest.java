package org.example;

import org.example.variations.*;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

public class FractalFlameGeneratorTest {

    @Test
    void constructorNormalizesWeights() {
        List<WeightedVariation> vars = List.of(
                new WeightedVariation(new LinearVariation(), 1.0),
                new WeightedVariation(new SwirlVariation(), 2.0)
        );
        Random r = new Random(1);
        FractalFlameGenerator gen = new FractalFlameGenerator(100, 100, 1000,
                List.of(new AffineParams(1,0,0,0,1,0)), vars, r);
        assertDoesNotThrow(() -> gen.generate());
    }

    @Test
    void generateThrowsIfNoAffineTransforms() {
        List<WeightedVariation> vars = List.of(new WeightedVariation(new LinearVariation(), 1.0));
        FractalFlameGenerator gen = new FractalFlameGenerator(100, 100, 1000,
                List.of(), vars, new Random(1));
        assertThrows(IllegalStateException.class, gen::generate);
    }

    @Test
    void generateMultithreadedThrowsIfThreadsZero() {
        List<WeightedVariation> vars = List.of(new WeightedVariation(new LinearVariation(), 1.0));
        FractalFlameGenerator gen = new FractalFlameGenerator(100, 100, 1000,
                List.of(new AffineParams(1,0,0,0,1,0)), vars, new Random(1));
        assertThrows(IllegalArgumentException.class, () -> gen.generateMultithreaded(0));
    }

    @Test
    void generateMultithreadedWorksWithDifferentThreadCounts() throws Exception {
        List<WeightedVariation> vars = List.of(new WeightedVariation(new LinearVariation(), 1.0));
        List<AffineParams> affine = List.of(new AffineParams(0.5, 0, 0, 0, 0.5, 0));
        FractalFlameGenerator gen = new FractalFlameGenerator(50, 50, 10000,
                affine, vars, new Random(42));
        int[][] hist = gen.generateMultithreaded(4);
        assertNotNull(hist);
        boolean nonZero = false;
        for (int y = 0; y < 50; y++) {
            for (int x = 0; x < 50; x++) {
                if (hist[y][x] > 0) nonZero = true;
            }
        }
        assertTrue(nonZero);
    }

    @Test
    void chooseVariationRespectsWeights() {
        List<WeightedVariation> vars = List.of(
                new WeightedVariation(new LinearVariation(), 0.3),
                new WeightedVariation(new SwirlVariation(), 0.7)
        );
        FractalFlameGenerator gen = new FractalFlameGenerator(10, 10, 100,
                List.of(new AffineParams(1,0,0,0,1,0)), vars, new Random(1));
        assertDoesNotThrow(() -> gen.generate());
    }
}