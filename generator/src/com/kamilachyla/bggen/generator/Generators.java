package com.kamilachyla.bggen.generator;

import com.kamilachyla.bggen.api.RectangleGenerator;
import com.kamilachyla.bggen.generator.types.SimpleGenerator;
import com.kamilachyla.bggen.generator.types.SquaresGenerator;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Generators {
    private static final Map<String, Supplier<RectangleGenerator>> suppliers =
            Map.of("simple", Generators::simple,
                    "squares", Generators::squaresGenerator);

    public static RectangleGenerator simple() {
        return new SimpleGenerator();
    }

    public static RectangleGenerator squaresGenerator() {
        return new SquaresGenerator();
    }

    public static Stream<RectangleGenerator> all() {
        return Stream.of(simple(), squaresGenerator());
    }

    public static Optional<RectangleGenerator> byName(String s) {
        Supplier<RectangleGenerator> sup = suppliers.getOrDefault(s,  () -> null);
        return Optional.ofNullable(sup.get());
    }

    public static List<String> getNames() {
        return Generators.all().map(RectangleGenerator::getName).collect(Collectors.toList());
    }

    public static String getDefaultName() {
        return "simple";
    }

}
