package kaantelypeli.ui;

import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import kaantelypeli.engine.Entity;
import kaantelypeli.engine.Level;
import kaantelypeli.fs.FileOperations;
import org.tinylog.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static kaantelypeli.engine.Entity.SCALE;
import static kaantelypeli.fs.FileOperations.loadSprite;

public class LevelEditor {

    public void editorMenu(Stage stage) {
        ChoiceDialog<Integer> choice = new ChoiceDialog<>();
        choice.initOwner(stage);
        choice.setHeaderText("Level to load");
        for (int i = 1; i <= 4; i++) {
            choice.getItems().add(i);
        }
        
        Optional<Integer> result = choice.showAndWait();
        result.ifPresent(input -> stage.setScene(editor(input, stage)));
    }
    
    public Scene editor(int level, Stage stage) {
        Pane pane = new Pane();
        pane.setPrefSize(240 * SCALE, 240 * SCALE);

        pane.setOnMouseClicked(c -> {
            int x = (int) c.getSceneX();
            int y = (int) c.getSceneY();
            editDialog(floorToScale(x), floorToScale(y), stage).ifPresent(node -> {
                pane.getChildren().add(node);
            });

            ArrayList<Entity> nodes = new ArrayList<>();
            pane.getChildren().stream().forEach(child -> nodes.add(new Entity(child.getId(),
                        (int) child.getLayoutX(), (int)child.getLayoutY()))
            );
            Logger.info(new Level(nodes).toJson());
        });
        
        Level l = FileOperations.loadLevel(level + "");
        l.getHitboxes().forEach(rect -> {
            rect.setOnMouseClicked(click -> editDialog((int) rect.getX(), (int)rect.getY(), stage));
            pane.getChildren().add(rect);
        });
        
        return new Scene(pane);
    }

    private static int floorToScale(int i) {
        return (i / (16 * SCALE)) * 16 * SCALE;
    }

    private static Optional<Rectangle> editDialog(int x, int y, Stage stage) {
        ChoiceBox<String> type = new ChoiceBox();
        TextField width = new TextField();
        TextField height = new TextField();
        type.setItems(FXCollections.observableArrayList(Arrays.asList(
                "door", "key", "lava", "player", "victory", "wall")));

        width.setPromptText("width");
        height.setPromptText("height");

        VBox inputs = new VBox();
        inputs.getChildren().addAll(type, width, height);

        TextInputDialog dialog = new TextInputDialog();
        dialog.initOwner(stage);
        dialog.setHeaderText("Editing tile [" + x + "," + y + "]");
        dialog.setContentText("Set tile type");
        dialog.setGraphic(null);
        dialog.setTitle(null);
        dialog.getDialogPane().setContent(inputs);

        Optional<String> result = dialog.showAndWait();
        Rectangle rect = new Rectangle(x, y, 0, 0);

        if (result.isPresent() && !type.getValue().equals("")) {
            rect.setId(type.getValue());
            rect.setFill(loadSprite(type.getValue(), SCALE));
            if (width.getText().equals("")) rect.setWidth(16 * SCALE);
            else rect.setWidth(Integer.parseInt(width.getText()) * SCALE);
            if (height.getText().equals("")) rect.setHeight(16 * SCALE);
            else rect.setHeight(Integer.parseInt(height.getText()) * SCALE);
            dialog.close();
            return Optional.of(rect);
        } else {
            return Optional.empty();
        }

    }
}
