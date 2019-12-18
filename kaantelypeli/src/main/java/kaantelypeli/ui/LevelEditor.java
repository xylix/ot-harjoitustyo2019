package kaantelypeli.ui;

import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
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

import static kaantelypeli.engine.Entity.SCALE;
import static kaantelypeli.utils.Parsing.valueOfWithDefault;

public class LevelEditor {
    final Stage stage;
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
            Logger.trace("Opened a copy of level " + input + " in editor");
        });
    }
    
    public Scene editor(int level) {
        Pane pane = new Pane();
        pane.setPrefSize(240 * SCALE, 240 * SCALE);


        editing = FileOperations.loadLevel(String.valueOf(level));

        pane.getChildren().addAll(editing.getHitboxes());
        ArrayList<Entity> entities = new ArrayList<>(editing.getEntities());

        VBox vbox = new VBox(pane);
        vbox.setPrefHeight((240 + 16)* SCALE );
        vbox.getChildren().add(saveButton(editing, stage));

        pane.setOnMouseClicked((MouseEvent click) -> {
            int x = (int) click.getSceneX();
            int y = (int) click.getSceneY();
            editDialog(floorToScale(x), floorToScale(y), stage).ifPresent(node -> {
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
        return (i / 16) * 16;
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
            int w = valueOfWithDefault(width.getText(), 16);
            int h = valueOfWithDefault(height.getText(), 16);

            final Entity e = new Entity(type.getValue(), x, y, w, h);
            return Optional.of(e.getHitbox());
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
                    fw.write(l.getJson());
                    Logger.trace("Saving file to " + saveLocation.getPath());
                } catch (IOException e) {
                    Logger.error(e);
                }
            } else {
                Logger.trace("Save dialog cancelled");
            }
        });
        return save;
    }
}
