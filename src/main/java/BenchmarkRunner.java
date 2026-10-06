package org.example;

import org.example.variations.*;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Random;

public class BenchmarkRunner {

    public static void main(String[] args) throws IOException {
        int width = 1024;
        int height = 768;
        int iterationCount = 100_000_000;
        long seed = 12345L;

        List<AffineParams> affineParams = List.of(
                new AffineParams(0.5, 0.0, 0.0, 0.0, 0.5, 0.0),
                new AffineParams(0.5, 0.0, 0.5, 0.0, 0.5, 0.0),
                new AffineParams(0.5, 0.0, 0.0, 0.0, 0.5, 0.5),
                new AffineParams(0.5, 0.0, 0.5, 0.0, 0.5, 0.5)
        );

        List<WeightedVariation> variations = List.of(
                new WeightedVariation(new SwirlVariation(), 1.0),
                new WeightedVariation(new HorseshoeVariation(), 0.8),
                new WeightedVariation(new LinearVariation(), 0.5)
        );

        Random random = new Random(seed);
        FractalFlameGenerator generator = new FractalFlameGenerator(width, height, iterationCount,
                affineParams, variations, random);

        int[] threads = {1, 2, 4, 8};
        PrintWriter writer = new PrintWriter(new FileWriter("benchmark_results.csv"));
        writer.println("Threads,TimeMs");

        for (int t : threads) {
            System.out.println("Running with " + t + " threads...");
            long start = System.nanoTime();
            int[][] histogram;
            if (t <= 1) {
                histogram = generator.generate();
            } else {
                histogram = generator.generateMultithreaded(t);
            }
            long end = System.nanoTime();
            long timeMs = (end - start) / 1_000_000;
            writer.println(t + "," + timeMs);
            System.out.println("Finished in " + timeMs + " ms");
        }
        writer.close();
        System.out.println("Benchmark results saved to benchmark_results.csv");
    }
}