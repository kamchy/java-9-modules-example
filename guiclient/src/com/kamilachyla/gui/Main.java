package com.kamilachyla.gui;

import com.kamilachyla.bggen.generator.Generators;
import com.kamilachyla.guimodel.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        var wProp = new NumericProperty("Szerokość", 800,10, 2048);
        var hProp = new NumericProperty("Wysokość", 600, 10, 2048);
        var fNameProp = new StringProperty("Nazwa pliku", "image.png");
        var genNamesProp = new ListProperty<String>("Generatory", Generators.getNames());
        var currGenProp = new StringProperty("Wybrany", Generators.getDefaultName());

        var imageProp = new ImageProperty("Image", wProp, hProp, fNameProp);
        SwingUtilities.invokeLater(() -> runWithPrefs(
                new Prefs(800, 600, "Generowanie tła"),
                new DrawModel(
                        wProp,
                        hProp,
                        fNameProp,
                        genNamesProp,
                        imageProp,
                        currGenProp)));
    }

    private static void runWithPrefs(Prefs prefs, DrawModel drawModel) {

        var frame = new JFrame(prefs.title());
        frame.setSize(prefs.width(), prefs.heigh());
        frame.add(createMainPanel(drawModel));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private static JPanel createMainPanel(DrawModel drawModel) {
        var p = new JPanel(new BorderLayout());
        p.add(createSettingsPanel(drawModel), BorderLayout.EAST);
        p.add(createImagePanel(drawModel), BorderLayout.CENTER);
        var bottom = new JPanel();
        var la = new BoxLayout(bottom, BoxLayout.Y_AXIS);
        bottom.setLayout(la);
        bottom.add(createButtonsPanel(drawModel));
        p.add(bottom, BorderLayout.SOUTH);
        return p;
    }

    private static JComponent createButtonsPanel(DrawModel drawModel) {
        var p = new JPanel();
        var la = new BoxLayout(p, BoxLayout.LINE_AXIS);
        var errorLabel = new JLabel();

        var buGenerate = new JButton("Generuj");
        buGenerate.addActionListener(e -> {
            drawModel.update();
        });
        var buSave = new JButton("Zapisz");
        buSave.addActionListener(e -> {
            try {
                drawModel.save();
            } catch (IOException ioException) {
                errorLabel.setText(ioException.getMessage());
            }
        });
        var buExit = new JButton("Wyjście");
        buExit.addActionListener(e -> {
            System.exit(0);
        });

        p.add(Box.createHorizontalGlue());
        p.add(buGenerate);
        p.add(Box.createHorizontalStrut(10));
        p.add(buSave);
        p.add(Box.createHorizontalStrut(10));
        p.add(buExit);
        p.setLayout(la);
        return p;
    }


    private static JComponent createImagePanel(DrawModel drawModel) {
        ImageProperty imageProperty = drawModel.getImageProperty();
        return new ImageViewer(imageProperty).component();
    }

    private static JComponent createSettingsPanel(DrawModel drawModel) {
        var p = new JPanel();
        var bl = new BoxLayout(p, BoxLayout.Y_AXIS);
        p.setLayout(bl);

        p.add(new NumericEditor(drawModel.getWidthProp()).component());
        p.add(new NumericEditor(drawModel.getHeightProp()).component());
        p.add(new FileEditor(drawModel.getFileNameProp()).component());
        p.add(new ListEditor(drawModel.getGeneratorNamesProp(), drawModel.getSelectedGeneratorNameProp()).component());
        p.add(Box.createVerticalStrut(500));
        return p;
    }


    record Prefs(int width, int heigh, String title){}
}
