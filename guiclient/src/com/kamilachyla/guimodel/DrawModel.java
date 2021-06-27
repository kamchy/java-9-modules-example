package com.kamilachyla.guimodel;

import com.kamilachyla.painter.Painter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class DrawModel {
    private final NumericProperty widthProp;
    private final NumericProperty heightProp;
    private final StringProperty fileNameProp;
    private final ListProperty<String> generatorNamesProp;
    private final ImageProperty imageProperty;
    private final StringProperty selectedGeneratorNameProp;

    private final Painter painter = new Painter();

    public DrawModel(NumericProperty widthProp, NumericProperty heightProp, StringProperty stringProp, ListProperty<String> generatorNamesProp, ImageProperty imageProperty, StringProperty currenrGeneratorProp) {
        this.widthProp = widthProp;
        this.heightProp = heightProp;
        this.fileNameProp = stringProp;
        this.generatorNamesProp = generatorNamesProp;
        this.imageProperty = imageProperty;
        this.selectedGeneratorNameProp = currenrGeneratorProp;
    }

    public NumericProperty getWidthProp() {
        return widthProp;
    }

    public NumericProperty getHeightProp() {
        return heightProp;
    }

    public StringProperty getFileNameProp() {
        return fileNameProp;
    }

    public ListProperty<String> getGeneratorNamesProp() {
        return generatorNamesProp;
    }

    public ImageProperty getImageProperty() {
        return imageProperty;
    }

    public List<Property<?>> properties() {
        return List.of(widthProp, heightProp, fileNameProp, imageProperty, generatorNamesProp, selectedGeneratorNameProp);
    }

    public void update() {
        var bi = new BufferedImage(widthProp.getValue().intValue(), heightProp.getValue().intValue(), BufferedImage.TYPE_INT_ARGB);
        imageProperty.setValue(bi);
        painter.paint(bi, selectedGeneratorNameProp.getValue());
    }

    public StringProperty getSelectedGeneratorNameProp() {
        return selectedGeneratorNameProp;
    }

    public void save() throws IOException {
        var img  = imageProperty.getValue();
        if (img != null) {
            File fileOut = new File(fileNameProp.getValue());
            ImageIO.write(img, "PNG", fileOut);
            System.out.println("File saved to "  + fileOut);
        }
    }
}

