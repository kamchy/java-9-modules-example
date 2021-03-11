package com.kamilachyla.guigen;

import com.kamilachyla.bggen.api.RectangleGenerator;
import com.kamilachyla.bggen.generator.Generators;
import com.kamilachyla.painter.Painter;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ImageGenerator {
    private final ImageData imageData;
    public ImageGenerator(ImageData imageData) {
        this.imageData = imageData;
    }

    public static void main(String[] args) {
        if ((args.length == 0) || ((args.length < 3) && (List.of("-h", "--help").contains(args[0])))) {
            usage(Arrays.asList(args.clone()));
            System.exit(0);
        }
        List<String> argsList = Arrays.stream(args).collect(Collectors.toList());
        int width = getArg(argsList, 0, Integer::parseInt).orElse(800);
        int height = getArg(argsList, 1, Integer::parseInt).orElse(600);
        File fileName = getArg(argsList, 2, File::new).orElse(generateTimeStampFilename());

        RectangleGenerator rectGenerator = getArg(argsList, 3, Generators::byName)
                .orElse(Optional.of(Generators.simple())).orElse(Generators.simple());
        ImageGenerator gen = new ImageGenerator(new ImageData(width, height, fileName));
        gen.generate(rectGenerator);
        System.out.printf("Generated %s [%sx%s]", fileName.getAbsolutePath(), width, height);
    }

    private static void usage(List<String> argsList) {
        System.out.printf("""
                                Usage: width height filename generatorName
                                where generatorName is one of %s.
                                Arguments given: %s""",
                Generators.getNames().toString(), argsList);
    }

    private void generate(RectangleGenerator rectangleGenerator) {
        Painter p = new Painter();
        p.paint(imageData.width(), imageData.height(), imageData.file(), rectangleGenerator);
    }

    private static <T> Optional<T> getArg(List<String> argsList, int idx, Function<String, T> conv) {
        try {
            T val = conv.apply(argsList.get(idx));
            return Optional.ofNullable(val);
        } catch (IndexOutOfBoundsException e) {
            return Optional.empty();
        }
    }

    private static File generateTimeStampFilename() {
        LocalDateTime dt = LocalDateTime.now();
        return new File(String.format("image_%s.png", dt.toString()));
    }
}
