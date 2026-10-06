package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AffineParamsTest {

    @Test
    void applyShouldComputeCorrectly() {
        AffineParams affine = new AffineParams(2.0, 1.0, 3.0, 0.5, 1.5, 2.0);
        Point p = new Point(1.0, 2.0);
        Point result = affine.apply(p);
        assertEquals(2.0*1.0 + 1.0*2.0 + 3.0, result.getX(), 1e-9);
        assertEquals(0.5*1.0 + 1.5*2.0 + 2.0, result.getY(), 1e-9);
    }
}

//mvn clean test jacoco:report
//open target/site/jacoco/index.html