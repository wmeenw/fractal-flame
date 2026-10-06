package org.example.variations;

import org.example.Point;
import static java.lang.Math.sin;
import static java.lang.Math.cos;

public class HeartVariation implements Variation {
    @Override
    public Point apply(Point p) {
        double x = p.getX();
        double y = p.getY();
        double newX = sin(x) * cos(y);
        double newY = sin(x) * sin(y);
        return new Point(newX, newY);
    }
}