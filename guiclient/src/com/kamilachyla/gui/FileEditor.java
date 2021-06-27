package com.kamilachyla.gui;

import com.kamilachyla.guimodel.Observer;
import com.kamilachyla.guimodel.Property;
import com.kamilachyla.guimodel.StringProperty;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.LabelView;
import java.awt.*;

public class FileEditor implements Observer {
    private final Property<String> prop;
    private final JPanel panel;
    private final LabelViewer fileLabel;
    private final JButton fileButton;
    private final JFileChooser fileDialog;

    public FileEditor(Property<String> stringProp) {
        this.prop = stringProp;
        this.fileLabel = new LabelViewer(this.prop);
        this.fileDialog = new JFileChooser();
        this.fileButton = createButton("Zmień plik", this.fileDialog);

        this.panel = createPanel(fileLabel, fileButton);
    }

    private JButton createButton(String action, JFileChooser fileDialog) {
        var bu = new JButton(action);
        bu.addActionListener(e -> {
            var res = fileDialog.showSaveDialog(this.panel);
            if (res == JFileChooser.APPROVE_OPTION) {
                this.prop.setValue(fileDialog.getSelectedFile().toString());
            }

        });
        return bu;
    }

    private JPanel createPanel(LabelViewer fileLabel, JButton fileButton) {
        var pa = new JPanel();
        var la = new BoxLayout(pa, BoxLayout.Y_AXIS);
        pa.setLayout(la);
        Box labelButton = Box.createHorizontalBox();
        labelButton.add(new Label(this.prop.getName()));
        labelButton.add(Box.createHorizontalStrut(10));
        labelButton.add(fileButton);
        pa.add(labelButton);
        pa.add(fileLabel.component());
        pa.setBorder(GuiHelper.createBorder());
        return pa;
    }

    public JPanel component() {
        return panel;

    }
    @Override
    public void changed() {
    }
}
