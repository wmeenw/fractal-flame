package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ConfigValidationTest {

    @Test
    void invalidWidthThrowsException() {
        Config config = Config.defaultConfig();
        config.setWidth(-10);
        ConfigValidator validator = new ConfigValidator();
        assertThrows(IllegalArgumentException.class, () -> validator.validate(config));
    }

    @Test
    void missingAffineParamsThrowsException() {
        Config config = Config.defaultConfig();
        config.getAffineParams().clear();
        ConfigValidator validator = new ConfigValidator();
        assertThrows(IllegalArgumentException.class, () -> validator.validate(config));
    }

    @Test
    void missingFunctionsThrowsException() {
        Config config = Config.defaultConfig();
        config.getFunctions().clear();
        ConfigValidator validator = new ConfigValidator();
        assertThrows(IllegalArgumentException.class, () -> validator.validate(config));
    }

    @Test
    void negativeHeightThrows() {
        Config config = Config.defaultConfig();
        config.setHeight(-100);
        assertThrows(IllegalArgumentException.class, () -> new ConfigValidator().validate(config));
    }

    @Test
    void zeroIterationCountThrows() {
        Config config = Config.defaultConfig();
        config.setIterationCount(0);
        assertThrows(IllegalArgumentException.class, () -> new ConfigValidator().validate(config));
    }

    @Test
    void nullOutputPathThrows() {
        Config config = Config.defaultConfig();
        config.setOutputPath(null);
        assertThrows(IllegalArgumentException.class, () -> new ConfigValidator().validate(config));
    }
}