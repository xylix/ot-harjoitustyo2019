package kaantelypeli.ui;

import javafx.scene.Scene;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import kaantelypeli.engine.Level;
import kaantelypeli.fs.FileOperations;

import java.util.Optional;
import java.util.stream.IntStream;

public class LevelEditor {
    private LevelEditor() {
        throw new IllegalStateException("Utility class should not be instantiated");
    }

    public static void editorMenu(Stage stage) {
        ChoiceDialog<Integer> choice = new ChoiceDialog<>();
        choice.initOwner(stage);
        choice.setHeaderText("Choose level to edit");
        for (int i = 1; i <= 4; i++) {
            choice.getItems().add(i);
        }
        
        Optional<Integer> result = choice.showAndWait();
        result.ifPresent(input -> {
            System.out.println("your input: " + input);
            stage.setScene(editor(input, stage));  
        });
    }
    
    public static Scene editor(int level, Stage stage) {
        Pane pane = new Pane();
        pane.setPrefSize(480, 480);
        IntStream.range(0, 15).forEach(i -> {
            IntStream.range(0, 15).forEach(j -> {
                Rectangle tile = new Rectangle(i * 32, j * 32, 32, 32);
                tile.setFill(Color.GRAY);
                tile.setStroke(Color.GREEN);

                tile.setOnMouseClicked(click -> textInputHandle(i * 32, j * 32, stage));
                pane.getChildren().add(tile);
            });
        });
        
        Level l = FileOperations.loadLevel(level + "");
        l.getHitboxes().forEach(rect -> {
            pane.getChildren().add(rect);
            rect.setOnMouseClicked(click -> {
                textInputHandle(rect.getX(), rect.getY(), stage);
            });
        });
        
        return new Scene(pane);
    }

    private static void textInputHandle(double x, double y, Stage stage) {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.initOwner(stage);
        dialog.setHeaderText("Editing tile [" + x / 2 + "," + y / 2 + "]");
        dialog.setContentText("Set tile type");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(input -> {
            System.out.println("your input: " + input);
            dialog.close();
        });
        
    }
}
