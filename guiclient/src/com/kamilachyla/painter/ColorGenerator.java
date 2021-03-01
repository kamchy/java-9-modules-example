package com.kamilachyla.painter;
import java.awt.*;
import java.util.Random;
import java.util.stream.Stream;

public final class ColorGenerator {
    private final float saturation;
    private final float brightness;
    private final Random rand = new Random();


    public ColorGenerator(float saturation, float brightness) {
        this.saturation = saturation;
        this.brightness = brightness;
    }

    Stream<Color> colorStream(Color initial) {
        return Stream.iterate(initial, this::nextColor);
    }

    private Color nextColor(Color c) {
        return Color.getHSBColor( rand.nextFloat(), saturation, brightness);
    }


    public static void main(String[] args) {
        var c = new ColorGenerator(0.7f, 0.6f);
        var s = c.colorStream(Color.getHSBColor(330f/360, 0.7f, 0.7f));
        s.limit(100).forEach(System.out::println);
    }
}
