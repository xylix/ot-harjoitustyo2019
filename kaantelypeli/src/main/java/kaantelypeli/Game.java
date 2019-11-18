package kaantelypeli;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
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
        display.setPrefSize(720, 480);
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
        return new Scene(display);
    }
    
}
