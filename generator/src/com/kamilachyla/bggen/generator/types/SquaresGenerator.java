package com.kamilachyla.bggen.generator.types;

import com.kamilachyla.bggen.api.Rect;
import com.kamilachyla.bggen.api.RectangleGenerator;

import java.util.stream.Stream;

public class SquaresGenerator implements RectangleGenerator {

    @Override
    public Stream<Rect> generate(double wi, double hi) {
        Stream.Builder<Rect> builder = Stream.builder();
        generateAbs(0, 0, wi, hi, builder);
        return builder.build();

    }

    @Override
    public String getName() {
        return "Generates stream of squares with its side not smaller than 20";
    }

    private void generateAbs(double x, double y, double wi, double hi, Stream.Builder<Rect> builder) {
        double minSize = Math.min(wi, hi);
        boolean biggerWi = wi > hi;
        if (minSize > 20) {
            builder.add(Rect.from(x, y, minSize, minSize));
            if (biggerWi) {
                generateAbs(x + minSize, y, wi - minSize, hi, builder);
            } else {
                generateAbs(x, y + minSize, wi, hi - minSize, builder);
            }
        }
    }
}
