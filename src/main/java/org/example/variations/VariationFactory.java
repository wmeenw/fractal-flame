package org.example.variations;

public class VariationFactory {
    public static Variation create(String name) {
        switch (name.toLowerCase()) {
            case "linear": return new LinearVariation();
            case "sinusoidal": return new SinusoidalVariation();
            case "swirl": return new SwirlVariation();
            case "horseshoe": return new HorseshoeVariation();
            case "heart" : return new HeartVariation();
            default: throw new IllegalArgumentException("Unknown variation: " + name);
        }
    }
}