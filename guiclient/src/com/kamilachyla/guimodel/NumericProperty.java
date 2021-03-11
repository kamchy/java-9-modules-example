package com.kamilachyla.guimodel;

public class NumericProperty extends Property<Number>{
    private final int from;
    private final int to;

    public NumericProperty(String name, int initial, int from, int to) {
        super(name, initial);
        this.from = from;
        this.to = to;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }
}
