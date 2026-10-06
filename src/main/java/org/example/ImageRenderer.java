package org.example;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageRenderer {
    public static void render(int[][] histogram, int width, int height, String outputPath) throws IOException {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        long maxCount = 0;
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++)
                if (histogram[y][x] > maxCount) maxCount = histogram[y][x];
        if (maxCount == 0) maxCount = 1;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int count = histogram[y][x];
                int rgb;
                if (count == 0) {
                    rgb = 0;
                } else {
                    double brightness = Math.log(count) / Math.log(maxCount);
                    int r = (int) (50 + brightness * 205);
                    int g = (int) (20 + brightness * 160);
                    int b = (int) (40 + brightness * 160);
                    rgb = (r << 16) | (g << 8) | b;
                }
                image.setRGB(x, y, rgb);
            }
        }
        ImageIO.write(image, "png", new File(outputPath));
    }
}