package org.example;

import org.example.variations.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class VariationTest {

    @Test
    void linearVariationShouldNotChangePoint() {
        Variation var = new LinearVariation();
        Point p = new Point(0.3, -0.7);
        Point result = var.apply(p);
        assertEquals(p.getX(), result.getX(), 1e-9);
        assertEquals(p.getY(), result.getY(), 1e-9);
    }

    @Test
    void sinusoidalVariationShouldApplySine() {
        Variation var = new SinusoidalVariation();
        Point p = new Point(0.5, 0.2);
        Point result = var.apply(p);
        assertEquals(Math.sin(0.5), result.getX(), 1e-9);
        assertEquals(Math.sin(0.2), result.getY(), 1e-9);
    }

    @Test
    void swirlVariationShouldComputeCorrectly() {
        Variation var = new SwirlVariation();
        Point p = new Point(0.5, 0.3);
        double x = p.getX(), y = p.getY();
        double r2 = x*x + y*y;
        double expectedX = x * Math.sin(r2) - y * Math.cos(r2);
        double expectedY = x * Math.cos(r2) + y * Math.sin(r2);
        Point result = var.apply(p);
        assertEquals(expectedX, result.getX(), 1e-9);
        assertEquals(expectedY, result.getY(), 1e-9);
    }

    @Test
    void horseshoeVariationShouldHandleZero() {
        Variation var = new HorseshoeVariation();
        Point p = new Point(0, 0);
        Point result = var.apply(p);
        assertEquals(0.0, result.getX(), 1e-9);
        assertEquals(0.0, result.getY(), 1e-9);
    }

    @Test
    void horseshoeVariationShouldComputeCorrectly() {
        Variation var = new HorseshoeVariation();
        Point p = new Point(3, 4);
        double r = Math.hypot(3, 4);
        double expectedX = (3 - 4) * (3 + 4) / r;
        double expectedY = 2 * 3 * 4 / r;
        Point result = var.apply(p);
        assertEquals(expectedX, result.getX(), 1e-9);
        assertEquals(expectedY, result.getY(), 1e-9);
    }

    @Test
    void heartVariationShouldComputeCorrectly() {
        Variation var = new HeartVariation();
        Point p = new Point(0.5, 0.3);
        double expectedX = Math.sin(0.5) * Math.cos(0.3);
        double expectedY = Math.sin(0.5) * Math.sin(0.3);
        Point result = var.apply(p);
        assertEquals(expectedX, result.getX(), 1e-9);
        assertEquals(expectedY, result.getY(), 1e-9);
    }
}