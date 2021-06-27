package com.kamilachyla.fxclient.gui;

import com.kamilachyla.fxclient.model.ApplicationModel;
import com.kamilachyla.fxclient.props.ApplicationProperties;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
        Scene scene = new Scene(createMainPane(), props.getWidth(), props.getHeight());
        stage.setScene(scene);
        stage.show();
    }

    private Parent createHBox(Node... nodes) {
        var parent = new HBox();
        parent.setSpacing(10);
        parent.getChildren().addAll(nodes);
        return parent;
    }

    private Parent createMainPane() {
        var bp = new BorderPane();
        bp.setCenter(createImageView());
        bp.setRight(createControls());
        bp.setBottom(createDebugPanel());
        return bp;
    }

    private Node createDebugPanel() {
        var la = new Label();

        final var info = StringBinding
                .stringExpression(model.width)
                .concat("x")
                .concat(model.height)
                .concat(" - ")
                .concat(model.currentGeneratorName);
        la.textProperty().bind(info);
        return la;
    }


    private Button createButton(String mnemonic, EventHandler<ActionEvent> handler) {
        Button button = new Button(mnemonic);
        button.setMnemonicParsing(true);
        button.setOnAction(handler);
        return button;
    }

    private Node createButtons() {
        Button buSave = createButton("_Save", (e) -> model.save());
        Button buGenerate = createButton("_Generate", (e) -> model.generate());
        Button buExit = createButton("E_xit", (e) -> Platform.exit());
        return createHBox(buSave, buGenerate, buExit);
    }

    private Node createControls() {
        var controls = new VBox();
        controls.setSpacing(5);
        controls.getChildren().add(labelledIntegerEntry("_Width", model.width));
        controls.getChildren().add(labelledIntegerEntry("_Height", model.height));
        controls.getChildren().add(labelledFileEntry("_File", model.file));
        controls.getChildren().add(labelledDropdown("G_enerators", model.renderers, model.currentGenerator, model.rendererConverter));
        controls.getChildren().add(createButtons());

        var titlePane = new TitledPane("Settings", controls);
        titlePane.setText("Settings");
        return titlePane;
    }

    private <T> Node labelledDropdown(String labelStr, SimpleListProperty<T> renderersProp,
                                      SimpleObjectProperty<T> currentGenerator, StringConverter<T> converter) {
        var label = new Label(labelStr);
        label.setMnemonicParsing(true);
        var spi = new ComboBox<T>();
        label.setLabelFor(spi);
        spi.itemsProperty().bindBidirectional(renderersProp);
        currentGenerator.bind(spi.getSelectionModel().selectedItemProperty());
        spi.setConverter(converter);
        spi.getSelectionModel().select(0);
        return createHBox(label, spi);
    }

    private Node labelledFileEntry(String file, SimpleStringProperty namePr) {
        var bu = createButton(file, (e) -> {
            File f = model.chooser.showOpenDialog(null);
            model.file.setValue(f.getName());
        });

        var la = new Label();
        la.textProperty().bindBidirectional(namePr);
        return createHBox(bu, la);
    }

    private Node labelledIntegerEntry(String labelContent, SimpleIntegerProperty textFieldValue) {
        var spi = new Spinner<Integer>();
        Label label = new Label(labelContent);
        label.setMnemonicParsing(true);
        label.setLabelFor(spi);
        var factory = new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 2048);
        spi.setEditable(true);
        factory.setAmountToStepBy(100);
        factory.valueProperty().bindBidirectional(textFieldValue.asObject());
        spi.setValueFactory(factory);
        // hack for spinner not updating backing prop when typing manually
        //https://stackoverflow.com/questions/32340476/manually-typing-in-text-in-javafx-spinner-is-not-updating-the-value-unless-user
        TextFormatter<Integer> formatter = new TextFormatter<>(factory.getConverter(), factory.getValue());
        formatter.valueProperty().bindBidirectional(textFieldValue.asObject());
        spi.getEditor().setTextFormatter(formatter);
        factory.valueProperty().bindBidirectional(formatter.valueProperty());
        return createHBox(label, spi);
    }

    private Node createImageView() {
        var iv = new ImageView();
        iv.imageProperty().bind(model.image);
        // add iv with StackPane as wrapper:
        // hack from https://www.javaer101.com/en/article/13369262.html
        var p = new ScrollPane();
//        stackPane.minWidthProperty().bind(
//                createDoubleBinding(() ->
//                p.getViewportBounds().getWidth(), p.viewportBoundsProperty()));
        /*stackPane.minWidthProperty().bind(model.width);
        stackPane.minHeightProperty().bind(model.height);
        p.setContent(stackPane);*/
        p.setContent(iv);
        p.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        p.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        return p;

    }


}