package com.kamilachyla.painter;

import com.kamilachyla.bggen.api.Rect;
import com.kamilachyla.bggen.api.RectangleGenerator;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.function.Supplier;

public final class Painter {

    public void paint(int width, int height, File file, RectangleGenerator rectangleGenerator) {
        ColorGenerator colorGen = new ColorGenerator(0.9f, 0.8f);
        var cgs = colorGen.colorStream(Color.getHSBColor(330f/360, 0.9f, 0.9f));
        Iterator<Color> colorIter = cgs.iterator();
        try {
            BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = bi.createGraphics();
            g.setBackground(colorIter.next());
            rectangleGenerator.generate(width, height).forEach(rect -> paintImage(g, rect, colorIter::next));
            ImageIO.write(bi, "PNG", file);

        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    private void paintImage(Graphics2D g, Rect r, Supplier<Color> next) {
        var p = next.get();
        g.setPaint(p);
        g.fillRect(
                Double.valueOf(r.getX()).intValue(),
                Double.valueOf(r.getY()).intValue(),
                Double.valueOf(r.getWidth()).intValue(),
                Double.valueOf(r.getHeight()).intValue());
    }
}
