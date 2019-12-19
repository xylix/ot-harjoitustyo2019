package kaantelypeli.ui;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import kaantelypeli.engine.Level;
import kaantelypeli.utils.FileOperations;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import static kaantelypeli.utils.FXUtils.button;

/**
 * Main Graphical User Interface class.
 */
public class Game extends Application {
    public static final int SCALE = 2;
    private Stage mainStage;
    private Scene mainMenu;
    /**
     * Launch the application.
     * @param args pass command line arguments to follow
     * main() method convention
    */
    public static void main(String[] args) {
        Application.launch();
    }
    
    @Override
    public void start(Stage stage) {
        mainStage = stage;
        VBox buttons = new VBox();
        InputStreamReader levelFolder = new InputStreamReader(Game.class.getResourceAsStream("/levels"));
        BufferedReader br = new BufferedReader(levelFolder);
        br.lines().forEach(line -> buttons.getChildren().add(levelButton(line, stage)));

        LevelEditor editor = new LevelEditor(stage, mainMenu);
        Button editorButton = button("editor", event -> editor.editorMenu());
        buttons.getChildren().add(editorButton);

        mainMenu = new Scene(buttons);
        stage.setScene(mainMenu);
        stage.show();
    }
    
    private Button levelButton(String file, Stage stage) {
        final String levelName = file.replace(".json", "");
        return button("level" + levelName, event -> {
            Level activeLevel = FileOperations.loadLevel(levelName);
            stage.setScene(toScene(activeLevel));
        });
    }
    
    private Scene toScene(Level level) {
        Pane pane = new Pane(level.getHitboxes().toArray(Rectangle[]::new));
        pane.setPrefHeight((240 + 14) * SCALE);

        Scene scene = new Scene(pane);
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.LEFT) {
                level.changeGravity(270);
                pane.setRotate(pane.getRotate() + 270);
            } else if (event.getCode() == KeyCode.RIGHT) {
                level.changeGravity(90);
                pane.setRotate(pane.getRotate() + 90);
            }
        });
        new AnimationTimer() {
            @Override
            public void handle(long timestamp) {
                level.gravitate();
                if (level.over()) {
                    mainStage.setScene(mainMenu);
                }
            }
        }.start();
        return scene;
    }
}
