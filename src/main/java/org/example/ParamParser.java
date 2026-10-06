package org.example;

import java.util.ArrayList;
import java.util.List;

public class ParamParser {

    public static List<AffineParams> parseAffineParams(String raw) {
        List<AffineParams> result = new ArrayList<>();
        if (raw == null || raw.isBlank()) return result;
        String[] blocks = raw.split("/");
        for (String block : blocks) {
            block = block.trim();
            if (block.isEmpty()) continue;
            String[] numbers = block.split(",");
            if (numbers.length != 6)
                throw new IllegalArgumentException("Affine block must have 6 numbers: " + block);
            try {
                double a = Double.parseDouble(numbers[0].trim());
                double b = Double.parseDouble(numbers[1].trim());
                double c = Double.parseDouble(numbers[2].trim());
                double d = Double.parseDouble(numbers[3].trim());
                double e = Double.parseDouble(numbers[4].trim());
                double f = Double.parseDouble(numbers[5].trim());
                result.add(new AffineParams(a, b, c, d, e, f));
            } catch (NumberFormatException ex) {
                throw new IllegalArgumentException("Invalid number in affine block: " + block, ex);
            }
        }
        return result;
    }

    public static List<Config.VariationConfig> parseFunctions(String raw) {
        List<Config.VariationConfig> result = new ArrayList<>();
        if (raw == null || raw.isBlank()) return result;
        String[] parts = raw.split(",");
        for (String part : parts) {
            part = part.trim();
            if (part.isEmpty()) continue;
            String[] pair = part.split(":");
            if (pair.length != 2)
                throw new IllegalArgumentException("Function pair must be name:weight, got " + part);
            String name = pair[0].trim();
            double weight = Double.parseDouble(pair[1].trim());
            result.add(new Config.VariationConfig(name, weight));
        }
        return result;
    }
}