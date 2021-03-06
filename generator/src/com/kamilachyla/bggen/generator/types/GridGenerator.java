package com.kamilachyla.bggen.generator.types;

import com.kamilachyla.bggen.api.Rect;
import com.kamilachyla.bggen.api.RectangleGenerator;

import java.util.stream.Stream;

public class GridGenerator implements RectangleGenerator {

    private static final int GRID_RECT_SIDE = 20;
    @Override
    public Stream<Rect> generate(double wi, double hi) {
        if (wi <= 0 || hi <= 0) {
            return Stream.empty();
        } else if (wi <= GRID_RECT_SIDE || hi <= GRID_RECT_SIDE) {
            return Stream.of(Rect.from(0, 0, wi, hi));
        }

        var sb = Stream.<Rect>builder();
        for (int x = 0; x < wi; x += GRID_RECT_SIDE) {
            for (int y = 0; y < hi; y += GRID_RECT_SIDE) {
                var width = x + GRID_RECT_SIDE > wi ? wi - x : GRID_RECT_SIDE;
                var height = y + GRID_RECT_SIDE > hi ? hi - y : GRID_RECT_SIDE;
                Rect rect = Rect.from(x, y, width, height);
                sb.add(rect);
            }
        }
        return sb.build();
    }

    @Override
    public String getName() {
        return "grid";
    }

    @Override
    public String getDescription() {
        return "Generates rectangles in a grid";
    }
}
