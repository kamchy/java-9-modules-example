package com.kamilachyla.guimodel;

import java.util.Arrays;
import java.util.List;

public class ListProperty<T> extends Property<List<T>> {
    public ListProperty(String name, List<T> initial) {
        super(name, initial);
        initial.forEach(System.out::println);
    }

    @Override
    public String getDebugString() {
        return Arrays.toString(getValue().toArray());
    }
}
