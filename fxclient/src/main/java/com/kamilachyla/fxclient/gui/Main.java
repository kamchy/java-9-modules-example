package com.kamilachyla.fxclient.gui;

import com.kamilachyla.bggen.api.RectangleGenerator;
import com.kamilachyla.bggen.generator.Generators;
import com.kamilachyla.fxclient.model.ApplicationModel;
import com.kamilachyla.fxclient.props.ApplicationProperties;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.File;

public class Main extends Application {
    private ApplicationProperties props = new ApplicationProperties();
    private ApplicationModel model = new ApplicationModel(props);

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(createPane(), props.getWidth(), props.getHeight());
        stage.setScene(scene);
        stage.show();
    }

    private Parent createPane() {
        var pane = new HBox();
        pane.getChildren().add(createImageView());
        pane.getChildren().add(createControls());
        return pane;
    }

    private Node createButtons() {
        var hbox = new HBox();
        Button buSave = new Button("_Save");
        buSave.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                    model.save();
                }
        );
        Button buGenerate = new Button("_Generate");
        buGenerate.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            model.generate();
        });
        hbox.getChildren().add(buSave);
        hbox.getChildren().add(buGenerate);
        return hbox;
    }

    private Node createControls() {
        var controls = new VBox();
        controls.getChildren().add(labelledIntegerEntry("Width", model.width));
        controls.getChildren().add(labelledIntegerEntry("Height", model.height));
        controls.getChildren().add(labelledFileEntry("File", model.file));
        controls.getChildren().add(labelledDropdown("Renderes", model.renderers));
        controls.getChildren().add(createButtons());
        return controls;
    }

    private Node labelledDropdown(String renderes, SimpleListProperty<RectangleGenerator> renderersProp) {
        var label = new Label(renderes);
        var spi = new ComboBox<RectangleGenerator>();
        spi.itemsProperty().bind(model.renderers);
        model.currentGenerator.bind(spi.getSelectionModel().selectedItemProperty());
        spi.setConverter(new StringConverter<>() {
            @Override
            public String toString(RectangleGenerator rectangleGenerator) {
                return rectangleGenerator.getName();
            }

            @Override
            public RectangleGenerator fromString(String s) {
                return Generators.byName(s).orElse(Generators.simple());
            }
        });
        spi.getSelectionModel().select(0);
        var hbox = new HBox();
        hbox.getChildren().add(label);
        hbox.getChildren().add(spi);
        return hbox;
    }

    private Node labelledFileEntry(String file, SimpleStringProperty namePr) {
        var bu = new Button(file);
        bu.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            File f = model.chooser.showOpenDialog(null);
            model.file.setValue(f.getName());


        });
        var la = new Label();
        la.setLabelFor(bu);
        la.textProperty().bindBidirectional(namePr);
        var pa = new HBox();
        pa.getChildren().addAll(la, bu);
        return pa;
    }

    private Node labelledIntegerEntry(String labelContent, SimpleIntegerProperty textFieldValue) {
        var hbox = new HBox();
        hbox.getChildren().add(new Label(labelContent));
        var spi = new Spinner<Integer>();
        SpinnerValueFactory.IntegerSpinnerValueFactory integerSpinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 1024);
        integerSpinnerValueFactory.setAmountToStepBy(10);
        integerSpinnerValueFactory.setValue(textFieldValue.getValue());
        spi.setValueFactory(integerSpinnerValueFactory);
        hbox.getChildren().add(spi);
        return hbox;
    }

    private Node createImageView() {
        var iv = new ImageView();
        iv.setImage(model.getImage());
        return iv;

    }

    private Node createRect() {
        var r = new Rectangle(800f, 800f);
        r.addEventHandler(MouseEvent.MOUSE_MOVED, (e) -> {
            Platform.runLater(() -> r.setFill(Paint.valueOf(colorStringFrom(r.getBoundsInLocal(), e.getX(), e.getY()))));
        });
        return r;
    }

    private String colorStringFrom(Bounds boundsInLocal, double x, double y) {
        var hue = (x / boundsInLocal.getWidth()) * 360;
        var sat = (y / boundsInLocal.getHeight()) * 100;
        var result = String.format("hsl(%s, %s%%, 90%%)", hue, sat);
        System.out.printf("%s%n", result);
        return result;
    }

}