package kaantelypeli.ui;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import kaantelypeli.engine.Level;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Optional;

import static kaantelypeli.utils.FXUtils.createButton;
import static kaantelypeli.utils.FXUtils.selector;
import static kaantelypeli.utils.FileOperations.loadLevel;

/**
 * Main Graphical User Interface class.
 */
public class Game extends Application {
    public static final int SCALE = 2;
    private Stage mainStage;
    private Scene mainMenu;
    public static final String FILESERVER =  "http://xylix.fi/levels";



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
        // Button cloud = createButton("cloud-levels", event -> cloudMenu(mainStage));
        LevelEditor editor = new LevelEditor(stage, mainMenu);
        Button editorButton = createButton("editor", event -> editor.editorMenu());
        // VBox buttons = new VBox(cloud, editorButton);
        VBox buttons = new VBox(editorButton);

        InputStreamReader levelFolder = new InputStreamReader(Game.class.getResourceAsStream("/levels"));
        BufferedReader br = new BufferedReader(levelFolder);
        br.lines().forEach(line -> buttons.getChildren().add(levelButton(line, stage)));

        mainMenu = new Scene(buttons);
        stage.setScene(mainMenu);
        stage.show();
    }


    /* private void cloudMenu(Stage target) {
        ChoiceDialog<String> choice = selector(FILESERVER, target);
        Optional<String> result = choice.showAndWait();
        result.ifPresent(input ->  target.setScene(levelToScene(loadLevel(FILESERVER + '/' + input));
    }
     */

    private Button levelButton(String file, Stage stage) {
        final String levelName = file.replace(".json", "");
        return createButton(levelName, event -> {
            Level activeLevel = loadLevel(levelName);
            stage.setScene(levelToScene(activeLevel));
        });
    }

    public Scene getNextScene(Level l) {
        final int nextLevel = l.levelIndex + 1;
        return switch(nextLevel) {
            case 666 -> this.mainMenu;
            case 1 -> levelToScene(loadLevel("2"));
            case 2 -> levelToScene(loadLevel("3"));
            case 3 -> levelToScene(loadLevel("4"));
            case 4 -> levelToScene(loadLevel("5"));
            case 5 -> this.mainMenu;
            default -> throw new IllegalStateException("Next level cannot be negative or more than amount of levels. Was %s".formatted(nextLevel));
        };
    }

    public Scene levelToScene(Level level) {
        Pane pane = new Pane(level.getHitboxes().toArray(Rectangle[]::new));
        Scene scene = new Scene(pane);

        scene.setOnKeyPressed(event -> {
            final KeyCode pressedKey = event.getCode();
            if (pressedKey == KeyCode.LEFT || pressedKey == KeyCode.A) {
                level.changeGravity(270);
                pane.setRotate(pane.getRotate() + 270);
            } else if (pressedKey == KeyCode.RIGHT || pressedKey == KeyCode.D) {
                level.changeGravity(90);
                pane.setRotate(pane.getRotate() + 90);
            } else if (pressedKey == KeyCode.R) {
                level.restart();
            }
        });
        new AnimationTimer() {
            @Override public void handle(long timestamp) {
                final Level.State gameState = level.tick();
                if (gameState == Level.State.WON) {

                    mainStage.setScene(getNextScene(level));
                }
            }
        }.start();
        return scene;
    }

}
