package com.kamilachyla.gui;

import com.kamilachyla.guimodel.Observer;
import com.kamilachyla.guimodel.Property;

import javax.swing.*;

public class LabelViewer implements Observer {
    private final Property<?> prop;
    private final JLabel label;

    public LabelViewer(Property<?> prop) {
        this.prop = prop;
        this.label = new JLabel();
        this.prop.addListener(this);
        changed();
    }

    public JComponent component() {
        return label;
    }

    @Override
    public void changed() {
        label.setText(prop.getDebugString());
    }
}
