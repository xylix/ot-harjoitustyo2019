package kaantelypeli.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import kaantelypeli.engine.Level;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import static kaantelypeli.utils.FXUtils.createButton;
import static kaantelypeli.utils.FileOperations.loadLevel;

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



    private Button levelButton(String file, Stage stage) {
        final String levelName = file.replace(".json", "");
        return createButton(levelName, event -> {
            Level activeLevel = loadLevel(levelName);
            stage.setScene(activeLevel.toScene(() -> mainStage.setScene(mainMenu)));
        });
    }

}
