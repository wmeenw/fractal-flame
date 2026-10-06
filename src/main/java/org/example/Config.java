package org.example;

import java.util.ArrayList;
import java.util.List;

public class Config {
    private int width = 1920;
    private int height = 1080;
    private int iterationCount = 2500;
    private long seed = 5L;
    private String outputPath = "result.png";
    private int threads = 1;
    private int symmetryLevel = 0;
    private List<AffineParams> affineParams = new ArrayList<>();
    private List<VariationConfig> functions = new ArrayList<>();

    public static class VariationConfig {
        private String name;
        private double weight;
        public VariationConfig() {}
        public VariationConfig(String name, double weight) {
            this.name = name; this.weight = weight;
        }
        public String getName() { return name; }
        public double getWeight() { return weight; }
        public void setName(String name) { this.name = name; }
        public void setWeight(double weight) { this.weight = weight; }
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public int getIterationCount() { return iterationCount; }
    public long getSeed() { return seed; }
    public String getOutputPath() { return outputPath; }
    public int getThreads() { return threads; }
    public int getSymmetryLevel() { return symmetryLevel; }
    public List<AffineParams> getAffineParams() { return affineParams; }
    public List<VariationConfig> getFunctions() { return functions; }

    public void setWidth(int width) { this.width = width; }
    public void setHeight(int height) { this.height = height; }
    public void setIterationCount(int iterationCount) { this.iterationCount = iterationCount; }
    public void setSeed(long seed) { this.seed = seed; }
    public void setOutputPath(String outputPath) { this.outputPath = outputPath; }
    public void setThreads(int threads) { this.threads = threads; }
    public void setSymmetryLevel(int symmetryLevel) { this.symmetryLevel = symmetryLevel; }
    public void setAffineParams(List<AffineParams> affineParams) { this.affineParams = affineParams; }
    public void setFunctions(List<VariationConfig> functions) { this.functions = functions; }

    public static Config defaultConfig() {
        Config config = new Config();
        config.getAffineParams().add(new AffineParams(1.0, 0.0, 0.0, 0.0, 1.0, 0.0));
        config.getFunctions().add(new VariationConfig("linear", 1.0));
        return config;
    }
}