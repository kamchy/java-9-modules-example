package com.kamilachyla.guimodel;

import java.util.function.Function;

public class Property<T> extends Observable {
    private final String name;
    private T value;

    public Property(String name, T initial) {
        this.name = name;
        this.value = initial;
    }

    public String getName() {
        return name;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        if ((this.value != value) && (value != null)) {
            System.out.printf("Property %s: changed from %s to %s%n", getName(), getDebugFn().apply(getValue()), getDebugFn().apply(value));
            this.value = value;
            changed();
        }
    }

    public Function<T, String> getDebugFn() {
        return Object::toString;
    }

    @Override
    public void changed() {
        super.changed();

    }

    public String getDebugString() {
        return getDebugFn().apply(getValue());
    }
}
