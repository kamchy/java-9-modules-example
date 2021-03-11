package com.kamilachyla.gui;

import com.kamilachyla.guimodel.ImageProperty;
import com.kamilachyla.guimodel.Observable;
import com.kamilachyla.guimodel.Observer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageViewer implements Observer {
    private final ImageProperty prop;
    private final JPanel canvas;
    private final JScrollPane pane;

    public ImageViewer(ImageProperty imageProperty) {
        this.prop = imageProperty;
        this.prop.addListener(this);
        this.canvas = new JPanel() {
            private static final long serialVersionUID = 1L;
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                BufferedImage value = prop.getValue();
                if (value != null) {
                    g.drawImage(value, 0, 0, null);
                }
            }
        };
        pane = new JScrollPane(canvas);
        changed();

    }

    public JComponent component() {
        return pane;
    }

    @Override
    public void changed() {
        System.out.println("Image viewer got changed, invalidates canvas.");
        canvas.setPreferredSize(new Dimension(prop.getWidth(), prop.getHeight()));
        canvas.repaint();
        pane.getViewport().revalidate();
    }
}
