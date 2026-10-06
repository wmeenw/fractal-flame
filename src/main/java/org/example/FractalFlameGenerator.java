package org.example;

import org.example.variations.WeightedVariation;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class FractalFlameGenerator {
    private final int width;
    private final int height;
    private final int iterationCount;
    private final List<AffineParams> affineTransforms;
    private final List<WeightedVariation> weightedVariations;
    private final double[] cumulativeWeights;
    private final Random random;

    public FractalFlameGenerator(int width, int height, int iterationCount,
                                 List<AffineParams> affineTransforms,
                                 List<WeightedVariation> weightedVariations,
                                 Random random) {
        this.width = width;
        this.height = height;
        this.iterationCount = iterationCount;
        this.affineTransforms = affineTransforms;
        this.weightedVariations = weightedVariations;
        this.random = random;

        double totalWeight = 0;
        for (WeightedVariation wv : weightedVariations) {
            totalWeight += wv.weight;
        }
        this.cumulativeWeights = new double[weightedVariations.size()];
        double accum = 0;
        for (int i = 0; i < weightedVariations.size(); i++) {
            accum += weightedVariations.get(i).weight / totalWeight;
            cumulativeWeights[i] = accum;
        }
    }

    private WeightedVariation chooseVariation() {
        double r = random.nextDouble();
        for (int i = 0; i < cumulativeWeights.length; i++) {
            if (r < cumulativeWeights[i]) {
                return weightedVariations.get(i);
            }
        }
        return weightedVariations.get(0);
    }

    private WeightedVariation chooseVariation(Random r) {
        double val = r.nextDouble();
        for (int i = 0; i < cumulativeWeights.length; i++) {
            if (val < cumulativeWeights[i]) {
                return weightedVariations.get(i);
            }
        }
        return weightedVariations.get(0);
    }

    public int[][] generate() {
        if (affineTransforms.isEmpty())
            throw new IllegalStateException("No affine transforms");
        System.out.println("Starting single-threaded generation...");
        int[][] histogram = new int[height][width];
        int step = iterationCount / 10;
        double x = random.nextDouble() * 2 - 1;
        double y = random.nextDouble() * 2 - 1;
        for (int i = 0; i < iterationCount; i++) {
            int idx = random.nextInt(affineTransforms.size());
            AffineParams affine = affineTransforms.get(idx);
            Point p = affine.apply(new Point(x, y));
            WeightedVariation wv = chooseVariation();
            Point np = wv.apply(p);
            int px = (int) ((np.getX() + 1) / 2 * width);
            int py = (int) ((np.getY() + 1) / 2 * height);
            if (px >= 0 && px < width && py >= 0 && py < height) {
                histogram[py][px]++;
            }
            x = np.getX();
            y = np.getY();
            if (step > 0 && (i + 1) % step == 0) {
                System.out.println(((i + 1) * 100 / iterationCount) + "% completed");
            }
        }
        System.out.println("Single-threaded generation finished.");
        return histogram;
    }

    public int[][] generateMultithreaded(int threads) {
        if (threads <= 0) throw new IllegalArgumentException("Threads must be positive");
        if (affineTransforms.isEmpty()) throw new IllegalStateException("No affine transforms");

        System.out.println("Starting multi-threaded generation with " + threads + " threads...");
        long startTime = System.currentTimeMillis();

        int[][] total = new int[height][width];
        Thread[] workers = new Thread[threads];
        int[][][] locals = new int[threads][][];
        int perThread = iterationCount / threads;
        int rem = iterationCount % threads;

        AtomicLong completedIterations = new AtomicLong(0);
        final int LOG_STEP = 1000;

        for (int t = 0; t < threads; t++) {
            final int tid = t;
            final int start = t * perThread;
            final int end = (t == threads - 1) ? start + perThread + rem : start + perThread;
            final int count = end - start;
            workers[t] = new Thread(() -> {
                int[][] localHist = new int[height][width];
                Random r = new Random(random.nextLong() + tid);
                double x = r.nextDouble() * 2 - 1;
                double y = r.nextDouble() * 2 - 1;
                int localIters = 0;
                for (int i = 0; i < count; i++) {
                    int idx = r.nextInt(affineTransforms.size());
                    AffineParams affine = affineTransforms.get(idx);
                    Point p = affine.apply(new Point(x, y));
                    WeightedVariation wv = chooseVariation(r);
                    Point np = wv.apply(p);
                    int px = (int) ((np.getX() + 1) / 2 * width);
                    int py = (int) ((np.getY() + 1) / 2 * height);
                    if (px >= 0 && px < width && py >= 0 && py < height) {
                        localHist[py][px]++;
                    }
                    x = np.getX();
                    y = np.getY();

                    localIters++;
                    if (localIters == LOG_STEP) {
                        completedIterations.addAndGet(LOG_STEP);
                        localIters = 0;
                    }
                }

                if (localIters > 0) {
                    completedIterations.addAndGet(localIters);
                }
                locals[tid] = localHist;
                System.out.println("Thread " + tid + " finished its " + count + " iterations.");
            });
            workers[t].start();
        }

        Thread progressThread = new Thread(() -> {
            int lastPercent = -1;
            while (completedIterations.get() < iterationCount) {
                long done = completedIterations.get();
                int percent = (int) (done * 100 / iterationCount);
                if (percent > lastPercent && percent % 10 == 0) {
                    System.out.println(percent + "% completed");
                    lastPercent = percent;
                }
                try { Thread.sleep(100); } catch (InterruptedException e) { break; }
            }
        });
        progressThread.start();

        for (Thread t : workers) {
            try { t.join(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }
        progressThread.interrupt();

        for (int t = 0; t < threads; t++) {
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    total[y][x] += locals[t][y][x];
                }
            }
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Multi-threaded generation finished in " + (endTime - startTime) + " ms");
        return total;
    }
}