package kaantelypeli;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.stage.Stage;
import kaantelypeli.engine.Level;
import static kaantelypeli.engine.Level.loadLevel;
 

public class Game extends Application {
    public static void main(String[] args) {
        Application.launch();
    }
    
    @Override
    public void start(Stage stage) {
        Pane display = new Pane();
        display.setPrefSize(480, 480);
        display.setBackground(new Background(new BackgroundImage(new Image("background.png"),
            BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
        
        Button button = new Button("play");
        button.setOnMouseClicked((MouseEvent t) -> {
            button.setText("loading");
            Level activeLevel = loadLevel(1);
            stage.setScene(toScene(display, activeLevel));
            // Implement the actual level loading method call
        });
        
        stage.setScene(new Scene(button));
        stage.show();
    }

    private Scene toScene(Pane display, Level level) {
        display.getChildren().addAll(level.entities);
        Scene scene = new Scene(display);
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.LEFT) {
                level.changeGravity(-90);
            } else if (event.getCode() == KeyCode.RIGHT) {
                level.changeGravity(90);
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
    
}
