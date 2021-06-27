package com.kamilachyla.gui;

import com.kamilachyla.guimodel.NumericProperty;

import javax.swing.*;
import java.awt.*;

public class NumericEditor {
    private final NumericProperty prop;
    private final JSpinner spinner;
    private final JPanel component;

    public NumericEditor(NumericProperty prop) {
        this.prop = prop;
        this.spinner = createSpinner(prop);
        this.component = createComponent(this.spinner);
    }

    private JSpinner createSpinner(NumericProperty prop) {
        var model = new SpinnerNumberModel(prop.getValue(), prop.getFrom(), prop.getTo(), 1);
        var s = new JSpinner(model);

        s.addChangeListener(e -> {
            prop.setValue((Integer) s.getValue());
        });
        return s;
    }

    private JPanel createComponent(JSpinner spinner) {
        var pa = new JPanel();
        var la = new BoxLayout(pa, BoxLayout.X_AXIS);
        pa.setLayout(la);
        pa.add(new JLabel(this.prop.getName()));
        pa.add(Box.createHorizontalStrut(10));
        pa.add(this.spinner);
        pa.add(Box.createHorizontalGlue());
        pa.setBorder(GuiHelper.createBorder());
        return pa;
    }

    public JComponent component() {
        return component;
    }
}
