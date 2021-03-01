package com.kamilachyla;

import com.kamilachyla.bggen.api.Rect;
import com.kamilachyla.bggen.api.RectangleGenerator;
import com.kamilachyla.bggen.generator.Generators;

import java.util.function.Consumer;

public class Main {
    private static final Consumer<Rect> rectConsumer = System.out::println;
    private static final Consumer<RectangleGenerator> printNameGeneratorConsumer =
            (RectangleGenerator g) -> System.out.println(g.getDescription());

    public static void main(String[] args) {
        double width = 800;
        double height = 600;
        Generators.all().forEach(printNameGeneratorConsumer.andThen(g -> g.generate(width, height).forEach(rectConsumer)));

    }
}
