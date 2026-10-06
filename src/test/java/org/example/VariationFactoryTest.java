package org.example;
import org.example.variations.*;
import org.example.variations.VariationFactory;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class VariationFactoryTest {

    @Test
    void createReturnsCorrectVariation() {
        assertTrue(VariationFactory.create("linear") instanceof LinearVariation);
        assertTrue(VariationFactory.create("sinusoidal") instanceof SinusoidalVariation);
        assertTrue(VariationFactory.create("swirl") instanceof SwirlVariation);
        assertTrue(VariationFactory.create("horseshoe") instanceof HorseshoeVariation);
        assertTrue(VariationFactory.create("heart") instanceof HeartVariation);
    }

    @Test
    void createThrowsOnUnknownName() {
        assertThrows(IllegalArgumentException.class, () -> VariationFactory.create("unknown"));
    }
}