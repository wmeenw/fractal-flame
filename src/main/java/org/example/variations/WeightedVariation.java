package org.example.variations;
import org.example.Point;

public class WeightedVariation implements Variation {
    public final Variation variation;
    public final double weight;

    public WeightedVariation(Variation variation, double weight) {
        this.variation = variation;
        this.weight = weight;
    }

    public Point apply(Point p) {
        Point result = variation.apply(p);
        return new Point(result.getX(), result.getY());
    }
}
