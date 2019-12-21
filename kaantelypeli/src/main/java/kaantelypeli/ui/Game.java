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
import java.util.Optional;

import static kaantelypeli.utils.FXUtils.createButton;
import static kaantelypeli.utils.FXUtils.selector;
import static kaantelypeli.utils.FileOperations.loadLevel;

/**
 * Main Graphical User Interface class.
 */
public class Game extends Application {
    public static final int SCALE = 2;
    public static final String FILESERVER =  "http://xylix.fi/levels";
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
        Button cloud = createButton("cloud-levels", event -> cloudMenu());
        LevelEditor editor = new LevelEditor(stage, mainMenu);
        Button editorButton = createButton("editor", event -> editor.editorMenu());
        VBox buttons = new VBox(cloud, editorButton);

        InputStreamReader levelFolder = new InputStreamReader(Game.class.getResourceAsStream("/levels"));
        BufferedReader br = new BufferedReader(levelFolder);
        br.lines().forEach(line -> buttons.getChildren().add(levelButton(line, stage)));

        mainMenu = new Scene(buttons);
        stage.setScene(mainMenu);
        stage.show();
    }

    private void cloudMenu() {
        ChoiceDialog<String> choice = selector(FILESERVER, mainStage);
        Optional<String> result = choice.showAndWait();
        result.ifPresent(input ->  mainStage.setScene(toScene(loadLevel(FILESERVER + '/' + input))));
    }

    private Button levelButton(String file, Stage stage) {
        final String levelName = file.replace(".json", "");
        return createButton(levelName, event -> {
            Level activeLevel = loadLevel(levelName);
            stage.setScene(toScene(activeLevel));
        });
    }
    
    private Scene toScene(Level level) {
        Pane pane = new Pane(level.getHitboxes().toArray(Rectangle[]::new));
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
            @Override public void handle(long timestamp) {
                level.tick();
                level.ifOver(() -> mainStage.setScene(mainMenu));
            }
        }.start();
        return scene;
    }
}
