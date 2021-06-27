package com.kamilachyla.fxclient.model;

import com.kamilachyla.bggen.api.Rect;
import com.kamilachyla.bggen.api.RectangleGenerator;
import com.kamilachyla.bggen.generator.Generators;
import com.kamilachyla.fxclient.props.ApplicationProperties;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ApplicationModel {

    public final SimpleIntegerProperty height;
    public final SimpleIntegerProperty width;
    public final SimpleStringProperty file;
    public final SimpleListProperty<RectangleGenerator> renderers;
    public final SimpleObjectProperty<WritableImage> image;
    public final FileChooser chooser;
    public final SimpleObjectProperty<RectangleGenerator> currentGenerator;
    public StringConverter<RectangleGenerator> rendererConverter = new RectangleGeneratorConverter();
    public final SimpleStringProperty currentGeneratorName;
    private final Canvas ca;
    private final Supplier<Color> colorSource;

    public ApplicationModel(ApplicationProperties props) {
        width = new SimpleIntegerProperty(props.getImageWidth());
        height = new SimpleIntegerProperty(props.getImageHeight());
        file = new SimpleStringProperty(props.getFileName());
        renderers = new SimpleListProperty<>(
                FXCollections.observableList(Generators.all().collect(Collectors.toList())));
        currentGenerator = new SimpleObjectProperty<>(Generators.squaresGenerator());
        currentGeneratorName = new SimpleStringProperty(currentGenerator.getName());
        currentGenerator.addListener((ob, oldv, newv) -> {
            if (newv != null) {
                currentGeneratorName.set(newv.getName());
            }
        });

        image = new SimpleObjectProperty<>(new WritableImage(props.getImageWidth(), props.getImageHeight()));
        chooser = new FileChooser();
        chooser.initialFileNameProperty().bind(file);

        ca = new Canvas();
        ca.widthProperty().bind(width);
        ca.heightProperty().bind(height);
        colorSource = new ColorSource();
        generate();
    }

    public Image getImage() {
        return image.get();
    }

    public void generate() {
        var gc = ca.getGraphicsContext2D();
        gc.clearRect(0, 0, ca.getWidth(), ca.getHeight());
        currentGenerator.get().generate(width.doubleValue(), height.doubleValue()).forEach(r -> paintRect(r, gc, colorSource));
        if ((width.intValue() != image.getValue().getWidth()) ||
                (height.intValue() != image.getValue().getHeight())) {
            image.setValue(new WritableImage(width.intValue(), height.intValue()));
        }
        ca.snapshot(null, image.get());
    }

    private void paintRect(Rect r, GraphicsContext gc, Supplier<Color> colorSupplier) {
        gc.setFill(colorSupplier.get());
        gc.fillRect(r.getX(), r.getY(), r.getWidth(), r.getHeight());
    }

    public void save() {
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image.get(), null), "png", new File(file.get()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ColorSource implements Supplier<Color> {

        @Override
        public Color get() {
            return Color.hsb(Math.random() * 360, 0.8, 0.8);
        }
    }

    private class RectangleGeneratorConverter extends StringConverter<RectangleGenerator> {
        @Override
        public String toString(RectangleGenerator rectangleGenerator) {
            return rectangleGenerator.getName();
        }

        @Override
        public RectangleGenerator fromString(String s) {
            return Generators.byName(s).orElse(Generators.simple());
        }
    }
}