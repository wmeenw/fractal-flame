package org.example.variations;

import org.example.Point;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class SwirlVariation implements Variation {
    @Override
    public Point apply(Point p) {
        double x = p.getX();
        double y = p.getY();
        double z = x*x + y*y;
        double newX = x*sin(z) - y*cos(z);
        double newY = x*cos(z) + y*sin(z);
        return new Point(newX, newY);
    }
}
