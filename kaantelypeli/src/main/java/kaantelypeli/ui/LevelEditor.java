package kaantelypeli.ui;

import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import kaantelypeli.engine.Entity;
import kaantelypeli.engine.Level;
import kaantelypeli.fs.FileOperations;
import org.tinylog.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static kaantelypeli.engine.Entity.SCALE;
import static kaantelypeli.fs.FileOperations.loadSprite;

public class LevelEditor {
    Stage stage;
    Level editing;

    public LevelEditor(Stage stage) {
        this.stage = stage;
    }

    public void editorMenu() {
        ChoiceDialog<Integer> choice = new ChoiceDialog<>();
        choice.initOwner(stage);
        choice.setHeaderText("Level to load");
        choice.setGraphic(null);
        choice.setTitle(null);

        for (int i = 1; i <= 4; i++) {
            choice.getItems().add(i);
        }
        
        Optional<Integer> result = choice.showAndWait();
        result.ifPresent(input ->  {
            stage.setScene(editor(input));
            Logger.info("Opened a copy of level " + input + " in editor");
        });
    }
    
    public Scene editor(int level) {
        Pane pane = new Pane();
        pane.setPrefSize(240 * SCALE, 240 * SCALE);

        ArrayList<Entity> nodes = new ArrayList<>();
        editing = FileOperations.loadLevel(level + "");

        editing.getHitboxes().forEach(rect -> {
            pane.getChildren().add(rect);
            nodes.add(new Entity(rect.getId(), (int) rect.getTranslateX(), (int) rect.getTranslateY()));
        });

        VBox vbox = new VBox(pane);
        vbox.setPrefHeight((240 + 16)* SCALE );
        vbox.getChildren().add(saveButton(editing, stage));

        pane.setOnMouseClicked((MouseEvent click) -> {
            int x = (int) click.getSceneX();
            int y = (int) click.getSceneY();
            editDialog(floorToScale(x), floorToScale(y), stage).ifPresent(node -> {
                pane.getChildren().add(node);
                final Entity e = new Entity(node.getId(),
                        (int) node.getX(), (int) node.getY(),
                        (int) node.getWidth()  / SCALE, (int) node.getHeight()  / SCALE);
                Logger.info(e.getJson());
                nodes.add(e);
            });
            editing = new Level(nodes);
        });

        return new Scene(vbox);
    }

    private static int floorToScale(int i) {
        return (i / (16 * SCALE)) * 16 * SCALE;
    }

    private static Optional<Rectangle> editDialog(int x, int y, Stage stage) {
        ChoiceBox<String> type = new ChoiceBox<>();
        TextField width = new TextField();
        TextField height = new TextField();
        type.setItems(FXCollections.observableArrayList(Arrays.asList("door", "key", "lava", "player", "victory", "wall")));

        width.setPromptText("width");
        height.setPromptText("height");

        VBox inputs = new VBox();
        inputs.setId("editInputs");
        inputs.getChildren().addAll(type, width, height);

        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText("Editing tile [" + x + "," + y + "]");
        dialog.setContentText("Set tile type");
        dialog.setGraphic(null);
        dialog.setTitle(null);
        dialog.getDialogPane().setContent(inputs);
        dialog.getDialogPane().getStyleClass().add("tileDialog");
        dialog.initOwner(stage);

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            final Rectangle rect = new Rectangle(x, y, 0, 0);

            rect.setId(type.getValue());
            rect.setFill(loadSprite(type.getValue(), SCALE));
            if (width.getText().equals("")) {
                rect.setWidth(16 * SCALE);
            } else  {
                rect.setWidth(Integer.parseInt(width.getText()) * SCALE);
            }
            if (height.getText().equals("")) {
                rect.setHeight(16 * SCALE);
            } else {
                rect.setHeight(Integer.parseInt(height.getText()) * SCALE);
            }
            return Optional.of(rect);
        } else {
            return Optional.empty();
        }
    }

    private static Button saveButton(Level l, Stage stage) {
        Button save = new Button("Save as");
        save.setOnAction(c -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialFileName("edited");
            fileChooser.setInitialDirectory(new File(LevelEditor.class.getClassLoader().getResource("levels").getFile()));
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(
                    "JSON files", "*.json"));
            File saveLocation = fileChooser.showSaveDialog(stage);
            // Null if user cancels dialog or OS dialog doesn't work.
            if (saveLocation != null) {
                try (FileWriter fw = new FileWriter(saveLocation)){
                    fw.write(l.toJson());
                    Logger.info("Saving file to " + saveLocation.getPath());
                } catch (IOException e) {
                    Logger.error(e);
                }
            } else {
                Logger.info("Save dialog cancelled");
            }
        });
        return save;
    }
}
