package org.example;

import org.example.variations.VariationFactory;
import org.example.variations.WeightedVariation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FlameApplication {
    private final ConfigLoader configLoader = new ConfigLoader();
    private final ConfigValidator validator = new ConfigValidator();

    public void run(String[] args) throws IOException {
        Config config = configLoader.load(args);
        validator.validate(config);

        List<AffineParams> affineList = config.getAffineParams();

        List<WeightedVariation> weightedVariations = new ArrayList<>();
        for (Config.VariationConfig vc : config.getFunctions()) {
            var variation = VariationFactory.create(vc.getName());
            weightedVariations.add(new WeightedVariation(variation, vc.getWeight()));
        }

        Random random = new Random(config.getSeed());
        FractalFlameGenerator generator = new FractalFlameGenerator(
                config.getWidth(), config.getHeight(),
                config.getIterationCount(),
                affineList, weightedVariations, random
        );

        int[][] histogram;
        if (config.getThreads() <= 1) {
            histogram = generator.generate();
        } else {
            histogram = generator.generateMultithreaded(config.getThreads());
        }

        if (config.getSymmetryLevel() > 1) {
            histogram = applySymmetry(histogram, config.getWidth(), config.getHeight(), config.getSymmetryLevel());
        }

        ImageRenderer.render(histogram, config.getWidth(), config.getHeight(), config.getOutputPath());
        System.out.println("Image saved to " + config.getOutputPath());
    }

    public int[][] applySymmetry(int[][] hist, int width, int height, int symmetryLevel) {
        if (symmetryLevel <= 1) return hist;
        int[][] newHist = new int[height][width];
        double centerX = width / 2.0;
        double centerY = height / 2.0;
        double angleStep = 2 * Math.PI / symmetryLevel;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int count = hist[y][x];
                if (count == 0) continue;
                double dx = x - centerX;
                double dy = y - centerY;
                for (int k = 0; k < symmetryLevel; k++) {
                    double angle = k * angleStep;
                    double newDx = dx * Math.cos(angle) - dy * Math.sin(angle);
                    double newDy = dx * Math.sin(angle) + dy * Math.cos(angle);
                    int newX = (int) Math.round(newDx + centerX);
                    int newY = (int) Math.round(newDy + centerY);
                    if (newX >= 0 && newX < width && newY >= 0 && newY < height) {
                        newHist[newY][newX] += count;
                    }
                }
            }
        }
        return newHist;
    }
}