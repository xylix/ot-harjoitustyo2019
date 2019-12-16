package kaantelypeli.ui;

import javafx.scene.Scene;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import kaantelypeli.engine.Entity;
import kaantelypeli.engine.Level;
import kaantelypeli.fs.FileOperations;
import org.tinylog.Logger;

import java.util.ArrayList;
import java.util.Optional;

import static kaantelypeli.fs.FileOperations.loadSprite;

public class LevelEditor {
    public static final int SCALE = 2;

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
            pane.getChildren().add(editDialog(floorToScale(x), floorToScale(y), stage));

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

    private static Rectangle editDialog(int x, int y, Stage stage) {
        TextField type = new TextField();
        TextField width = new TextField();
        TextField height = new TextField();
        type.setPromptText("type");
        width.setPromptText("width");
        height.setPromptText("height");

        VBox inputs = new VBox();
        inputs.getChildren().addAll(type, width, height);

        Dialog dialog = new TextInputDialog();
        dialog.initOwner(stage);
        dialog.setHeaderText("Editing tile [" + x + "," + y + "]");
        dialog.setContentText("Set tile type");
        dialog.getDialogPane().setContent(inputs);

        Optional<String> result = dialog.showAndWait();
        Rectangle rect = new Rectangle(x, y, 0, 0);
        result.ifPresent(input -> {
            rect.setId(type.getText());
            rect.setFill(loadSprite(type.getText(), SCALE));
            if (width.getText().equals("")) rect.setWidth(16 * SCALE);
            else rect.setWidth(Integer.parseInt(width.getText()) * SCALE);
            if (height.getText().equals("")) rect.setHeight(16 * SCALE);
            else rect.setHeight(Integer.parseInt(height.getText()) * SCALE);
            dialog.close();
        });

        return rect;
    }
}
