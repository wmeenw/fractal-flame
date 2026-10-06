package org.example;

public class ConfigValidator {
    public void validate(Config config) {
        if (config.getWidth() <= 0) throw new IllegalArgumentException("Width must be positive");
        if (config.getHeight() <= 0) throw new IllegalArgumentException("Height must be positive");
        if (config.getIterationCount() <= 0) throw new IllegalArgumentException("Iteration count must be positive");
        if (config.getThreads() <= 0) throw new IllegalArgumentException("Threads count must be positive");
        if (config.getOutputPath() == null || config.getOutputPath().isBlank())
            throw new IllegalArgumentException("Output path must be specified");
        if (config.getAffineParams().isEmpty())
            throw new IllegalArgumentException("At least one affine transformation required");
        if (config.getFunctions().isEmpty())
            throw new IllegalArgumentException("At least one variation required");
    }
}