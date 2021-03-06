package com.kamilachyla.bggen.api;

import java.util.Objects;
import java.util.StringJoiner;

public final class Rect {
    private final double x;
    private final double y;
    private final double width;
    private final double height;

    public static Rect from(double x, double y, double width, double height) {
        return new Rect(x, y, width, height);
    }

    private Rect(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rect rect = (Rect) o;
        return Double.compare(rect.x, x) == 0 &&
                Double.compare(rect.y, y) == 0 &&
                Double.compare(rect.width, width) == 0 &&
                Double.compare(rect.height, height) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, width, height);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Rect.class.getSimpleName() + "[", "]")
                .add("x=" + x)
                .add("y=" + y)
                .add("width=" + width)
                .add("height=" + height)
                .toString();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }



}
