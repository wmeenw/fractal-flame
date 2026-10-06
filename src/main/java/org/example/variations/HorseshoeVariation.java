package org.example.variations;

import org.example.Point;

import static java.lang.Math.sqrt;

public class HorseshoeVariation implements Variation {
    @Override
    public Point apply(Point p) {
        double x = p.getX();
        double y = p.getY();
        double z = sqrt(x*x + y*y);
        if (z == 0) return new Point(0, 0);
        double newX = (x - y) * (x + y) / z;
        double newY = 2 * x * y / z;
        return new Point(newX, newY);
    }
}
