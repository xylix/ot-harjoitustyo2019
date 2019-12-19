package kaantelypeli.ui;

import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import kaantelypeli.engine.Entity;
import kaantelypeli.engine.Level;
import kaantelypeli.utils.FileOperations;
import org.tinylog.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static kaantelypeli.ui.Game.SCALE;
import static kaantelypeli.utils.FXUtils.button;
import static kaantelypeli.utils.Parsing.valueOfWithDefault;

public class LevelEditor {
    private final Stage stage;
    private Level editing;
    private final Scene mainMenu;

    /**
     * Create a new LevelEditor object.
     * @param stage Parent stage for the opened editor
     */
    public LevelEditor(Stage stage, Scene mainMenu) {
        this.stage = stage;
        this.mainMenu = mainMenu;
    }

    /**
     * Entry point, starts a selection dialog
     */
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
            Logger.trace("Opened a copy of level " + input + " in editor");
        });
    }
    
    private Scene editor(int level) {
        Pane pane = new Pane();
        pane.setPrefSize(240 * SCALE, 240 * SCALE);

        editing = FileOperations.loadLevel(String.valueOf(level));
        pane.getChildren().addAll(editing.getHitboxes());
        ArrayList<Entity> entities = new ArrayList<>(editing.getEntities());
        VBox vbox = new VBox(pane);
        vbox.setPrefHeight((240 + 16) * SCALE);
        HBox buttons = new HBox(saveButton());
        buttons.getChildren().add(button("menu", event -> stage.setScene(mainMenu)));
        vbox.getChildren().add(buttons);

        pane.setOnMouseClicked((MouseEvent click) -> {
            int x = (int) click.getSceneX();
            int y = (int) click.getSceneY();
            tileEditDialog(floorToScale(x), floorToScale(y), stage).ifPresent(node -> {
                pane.getChildren().add(node);
                final Entity e = new Entity(node.getId(),
                        (int) node.getX() / SCALE, (int) node.getY() / SCALE,
                        (int) node.getWidth() / SCALE, (int) node.getHeight() / SCALE);
                entities.add(e);
                Logger.trace(e.getJson());
            });
            editing = new Level(entities);
        });
        return new Scene(vbox);
    }

    private static int floorToScale(int i) {
        return (i / (16 * SCALE)) * (16 * SCALE) / SCALE;
    }

    private static Optional<Rectangle> tileEditDialog(int x, int y, Stage stage) {
        ChoiceBox<String> type = new ChoiceBox<>();
        type.setItems(FXCollections.observableArrayList(Arrays.asList("wall", "victory", "player", "lava", "key", "door")));
        TextField width = new TextField();
        width.setPromptText("width");
        TextField height = new TextField();
        height.setPromptText("height");

        VBox inputs = new VBox(type, width, height);
        inputs.setId("editInputs");

        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText("Adding a tile at [" + x + "," + y + "]");
        dialog.setTitle("Editing");
        dialog.setGraphic(null);
        dialog.getDialogPane().setContent(inputs);
        dialog.getDialogPane().getStyleClass().add("tileDialog");
        dialog.initOwner(stage);
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            int w = valueOfWithDefault(width.getText(), 16);
            int h = valueOfWithDefault(height.getText(), 16);
            final Entity e = new Entity(type.getValue(), x, y, w, h);
            return Optional.of(e.getHitbox());
        } else {
            return Optional.empty();
        }
    }

    private Button saveButton() {
        return button("save", (c -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialFileName("edited");
            fileChooser.setInitialDirectory(new File(LevelEditor.class.getClassLoader().getResource("levels").getFile()));
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(
                    "JSON files", "*.json"));
            File saveLocation = fileChooser.showSaveDialog(stage);
            // Null if user cancels dialog or OS dialog doesn't work.
            if (saveLocation != null) {
                try (FileWriter fw = new FileWriter(saveLocation)) {
                    fw.write(editing.getJson());
                    Logger.trace("Saving file to " + saveLocation.getPath());
                } catch (IOException e) {
                    Logger.error(e);
                }
            } else {
                Logger.trace("Save dialog cancelled");
            }
        }));
    }
}
