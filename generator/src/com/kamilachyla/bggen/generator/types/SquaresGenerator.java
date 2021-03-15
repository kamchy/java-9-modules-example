package com.kamilachyla.bggen.generator.types;

import com.kamilachyla.bggen.api.Rect;
import com.kamilachyla.bggen.api.RectangleGenerator;

import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class SquaresGenerator implements RectangleGenerator {

    private static final Random rand = new Random();
    @Override
    public Stream<Rect> generate(double wi, double hi) {
        Stream.Builder<Rect> builder = Stream.builder();
        generateAbs(Rect.from(0, 0, wi, hi), builder);
        return builder.build();
    }

    @Override
    public String getName() {
        return "squares";
    }

    @Override
    public String getDescription() {
        return "Generates stream of squares with its side not smaller than 20";
    }

    record Point(double x, double y) {
        Point add(Point p) {
            return new Point(x + p.x, y + p.y);
        }
    }

    private Stream<Rect> split(Rect r) {
        double min = Math.min(r.getWidth(), r.getHeight());
        boolean odd = min % 2 == 0;
        int delta = odd ? 1 : 0;
        var half = Math.round((odd ? (min - 1) : min) / 2.0);
        return List.of(
                Rect.from(r.getX(), r.getY(), half, half),
                Rect.from(r.getX() + half, r.getY(), half + delta, half),
                Rect.from(r.getX(), r.getY() + half + delta, half, half + delta),
                Rect.from(r.getX() + half, r.getY() + half, half + delta, half + delta))
                .stream();
    }

    private void generateAbs(Rect r, Stream.Builder<Rect> builder) {
        double minSize = Math.min(r.getWidth(), r.getHeight());
        boolean biggerWi = r.getWidth() > r.getHeight();
        if ((r.getWidth() > 0) && (r.getHeight() > 0) && (minSize > 10)) {
            builder.add(Rect.from(r.getX(), r.getY(), minSize, minSize));
            split(r).forEach(quad -> {
                if (rand.nextBoolean()) {
                    generateAbs(quad, builder);
                } else {
                    builder.add(quad);
                }
            });

            var rightRect = Rect.from(r.getX() + minSize, r.getY(), r.getWidth() - minSize, r.getHeight());
            var bottomRect = Rect.from(r.getX() , r.getY() + minSize, r.getWidth(), r.getHeight() - minSize);
            generateAbs( biggerWi ? rightRect : bottomRect, builder);
        }
    }
}
