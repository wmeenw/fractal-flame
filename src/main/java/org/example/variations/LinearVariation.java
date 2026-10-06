package org.example.variations;

import org.example.Point;

public class LinearVariation implements Variation {
    @Override
    public Point apply(Point p) {
        return new Point(p.getX(), p.getY());
    }
}
