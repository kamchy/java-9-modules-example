package com.kamilachyla.gui;

import com.kamilachyla.guimodel.ListProperty;
import com.kamilachyla.guimodel.Observer;
import com.kamilachyla.guimodel.StringProperty;

import javax.swing.*;
import java.awt.*;

import static java.awt.event.ItemEvent.SELECTED;

public class ListEditor implements Observer {
    private final JComboBox<String> fld;
    private final ListProperty<String> listProp;
    private final JComponent component;
    private final StringProperty selectedProp;

    public ListEditor(ListProperty<String> listProp, StringProperty selectedProp) {
        this.listProp = listProp;
        this.selectedProp = selectedProp;

        JLabel lab = new JLabel(listProp.getName());
        fld = new JComboBox<>();
        fld.addItemListener(e -> {
            if (e.getStateChange() == SELECTED) {
                selectedProp.setValue(e.getItem().toString());
            }
        });
        listProp.addListener(this);
        component = createPanel(lab, fld);
        changed();

    }

    private JComponent createPanel(JLabel lab, JComboBox<String> fld) {
        var pa = new JPanel();
        var la = new BoxLayout(pa, BoxLayout.X_AXIS);
        pa.setLayout(la);
        pa.add(lab);
        lab.setLabelFor(fld);
        pa.add(Box.createHorizontalStrut(10));
        pa.add(fld);
        pa.setBorder(GuiHelper.createBorder());
        return pa;
    }

    public JComponent component() {
        return component;
    }

    @Override
    public void changed() {
        fld.removeAllItems();
        for (String val : listProp.getValue()) {
            System.out.printf("changed in list editor %s added %s", listProp.getName(), val);
            fld.addItem(val);
        }
        component.repaint();
    }
}
