package org.example.variations;

import org.example.Point;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WeightedVariationTest {

    @Test
    void applyDelegatesToWrappedVariation() {
        Variation linear = new LinearVariation();
        WeightedVariation wv = new WeightedVariation(linear, 0.5);
        Point p = new Point(0.3, 0.7);
        Point result = wv.apply(p);
        assertEquals(0.3, result.getX(), 1e-9);
        assertEquals(0.7, result.getY(), 1e-9);
    }
}