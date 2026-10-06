package org.example;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

public class ConfigLoaderTest {

    @Test
    void loadFromJsonShouldOverrideDefaults() throws IOException {
        String[] args = {"--config", "src/test/resources/test_config.json"};
        ConfigLoader loader = new ConfigLoader();
        Config config = loader.load(args);
        assertEquals(800, config.getWidth());
        assertEquals(600, config.getHeight());
        assertEquals(10000, config.getIterationCount());
        assertEquals("test_output.png", config.getOutputPath());
        assertEquals(2, config.getThreads());
        assertEquals(12345L, config.getSeed());
        assertEquals(3, config.getSymmetryLevel());
        assertEquals(1, config.getFunctions().size());
        assertEquals("swirl", config.getFunctions().get(0).getName());
        assertEquals(1.0, config.getFunctions().get(0).getWeight());
        assertEquals(1, config.getAffineParams().size());
        assertEquals(0.5, config.getAffineParams().get(0).getA());
    }

    @Test
    void commandLineOverridesJson() throws IOException {
        String[] args = {"--config", "src/test/resources/test_config.json", "-w", "1024", "-t", "4"};
        ConfigLoader loader = new ConfigLoader();
        Config config = loader.load(args);
        assertEquals(1024, config.getWidth()); // from CLI
        assertEquals(600, config.getHeight()); // from JSON
        assertEquals(4, config.getThreads()); // from CLI
        assertEquals("swirl", config.getFunctions().get(0).getName()); // from JSON
    }

    @Test
    void defaultConfigWhenNoArgs() throws IOException {
        String[] args = {};
        ConfigLoader loader = new ConfigLoader();
        Config config = loader.load(args);
        assertEquals(1920, config.getWidth());
        assertEquals(1080, config.getHeight());
        assertEquals(2500, config.getIterationCount());
        assertEquals("result.png", config.getOutputPath());
        assertEquals(1, config.getThreads());
    }

    @Test
    void loadWithMissingConfigFileThrows() {
        String[] args = {"--config", "missing.json"};
        ConfigLoader loader = new ConfigLoader();
        assertThrows(IOException.class, () -> loader.load(args));
    }

    @Test
    void commandLineWithUnknownArgumentsIgnoresThem() throws IOException {
        String[] args = {"--unknown", "value", "-w", "800"};
        ConfigLoader loader = new ConfigLoader();
        Config config = loader.load(args);
        assertEquals(800, config.getWidth());
    }
}