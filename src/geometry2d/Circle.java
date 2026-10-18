package geometry2d;

import exceptions.InvalidParameterException;
import java.io.Serializable;

public class Circle implements Figure, Serializable {
    private static final long serialVersionUID = 1L;

    private final double radius;

    public Circle(double radius) {
        if (!Double.isFinite(radius) || radius <= 0) {
            throw new InvalidParameterException("radius", "должен быть положительным конечным числом");
        }
        this.radius = radius;
    }

    public double getRadius() { return radius; }

    @Override
    public double area() { return Math.PI * radius * radius; }

    @Override
    public double perimeter() { return 2 * Math.PI * radius; }

    @Override
    public String toString() { return "Circle{radius=" + radius + "}"; }
}