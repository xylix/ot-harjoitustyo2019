package kaantelypeli;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import static kaantelypeli.Level.loadLevel;
 

public class Game extends Application {
    public static void main(String[] args) {
        Application.launch();
    }
    
    @Override
    public void start(Stage stage) {
        Pane display = new Pane();
        display.setPrefSize(720, 480);
        
        Button play = new Button("play");
        play.setOnMouseClicked((MouseEvent t) -> {
            play.setText("loading");
            Level activeLevel = loadLevel(1);
            stage.setScene(toScene(display, activeLevel));
            // Implement the actual level loading method call
        });
        
        stage.setScene(new Scene(play));
        stage.show();
    }

    private Scene toScene(Pane display, Level level) {
        display.getChildren().addAll(level.entities);
        return new Scene(display);
    }
    
}
