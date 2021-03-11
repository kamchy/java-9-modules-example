package com.kamilachyla.guimodel;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable {
    private final List<Observer> observers = new ArrayList<>();
    public void addListener(Observer o) {
        if (!observers.contains(o)) {
            this.observers.add(o);
        }
    }

    public void removeListener(Observer o) {
        observers.remove(o);
    }

    public void changed() {
        for (var o : observers) {
            o.changed();
        }
    }

}
