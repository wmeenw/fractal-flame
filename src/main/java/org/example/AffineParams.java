package org.example;

public class AffineParams {
    private final double a;
    private final double b;
    private final double c;
    private final double d;
    private final double e;
    private final double f;

    public AffineParams(double a, double b, double c, double d, double e, double f) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
        this.f = f;
    }

    public Point apply(Point p) {
        double newX = a * p.getX() + b * p.getY() + c;
        double newY = d * p.getX() + e * p.getY() + f;
        return new Point(newX, newY);
    }

    public double getA() { return a; }
    public double getB() { return b; }
    public double getC() { return c; }
    public double getD() { return d; }
    public double getE() { return e; }
    public double getF() { return f; }
}
