package com.kamilachyla.bggen.generator.types;

import com.kamilachyla.bggen.api.Rect;
import com.kamilachyla.bggen.api.RectangleGenerator;

import java.util.stream.Stream;

public class SimpleGenerator implements RectangleGenerator {

    @Override
    public Stream<Rect> generate(double wi, double hi) {
        return Stream.of(Rect.from(0, 0, wi, hi));
    }

    @Override
    public String getName() {
        return "Generates single Rect with x=0, y=0 and given width and height";
    }
}
