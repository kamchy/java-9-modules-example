package com.kamilachyla.bggen.generator;

import com.kamilachyla.bggen.api.RectangleGenerator;
import com.kamilachyla.bggen.generator.types.SimpleGenerator;
import com.kamilachyla.bggen.generator.types.SquaresGenerator;

import java.util.stream.Stream;

public final class Generators {
    public static RectangleGenerator simple() {
        return new SimpleGenerator();
    }

    public static RectangleGenerator squaresGenerator() {
        return new SquaresGenerator();
    }

    public static Stream<RectangleGenerator> all() {
        return Stream.of(simple(), squaresGenerator());
    }
}
