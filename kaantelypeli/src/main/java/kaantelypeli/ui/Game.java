package kaantelypeli.ui;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import kaantelypeli.engine.Level;
import static kaantelypeli.engine.Level.loadLevel;
 
/**
 * Main Graphical User Interface class.
 * @author xylix
 */
public class Game extends Application {
    /**
     * Launch the application.
    */
    public static void main() {
        Application.launch();
    }
    
    @Override
    public void start(Stage stage) {
        Pane pane = new Pane();
        pane.setPrefSize(240, 240);
        
        GridPane buttons = new GridPane();
        for (int i = -1; i <= 1; i++) {
            buttons.add(levelButton(i, stage, pane), 0, i + 1);
        }
        
        stage.setScene(new Scene(buttons));
        stage.show();
    }

    private Scene toScene(Pane pane, Level level) {
        pane.getChildren().addAll(level.getEntities());
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
            public void handle(long nykyhetki) {
                level.gravitate();
            }
        }.start();
        return scene;
    }

    private Button levelButton(int i, Stage stage, Pane pane) {
        Button level = new Button("level " + i);
        level.getStyleClass().add("button" + i);
        level.setOnMouseClicked((MouseEvent t) -> {
            level.setText("loading");
            Level activeLevel = loadLevel(i);
            stage.setScene(toScene(pane, activeLevel));
        });
        return level;
    }
    
}
