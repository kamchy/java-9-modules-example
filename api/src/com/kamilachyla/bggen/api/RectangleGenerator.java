package com.kamilachyla.bggen.api;

import java.util.stream.Stream;

public interface RectangleGenerator {
    Stream<Rect> generate(double wi, double hi);
    String getName();
    String getDescription();
}
