package org.example.variations;

import org.example.Point;

import static java.lang.Math.sin;

public class SinusoidalVariation implements Variation {
    @Override
    public Point apply(Point p) {
        double x = p.getX();
        double y = p.getY();
        double newX = sin(x);
        double newY = sin(y);
        return new Point(newX, newY);
    }
}
