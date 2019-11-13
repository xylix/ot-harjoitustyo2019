package kaantelypeli;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import static kaantelypeli.Level.loadLevel;
 

public class Game extends Application {
    public static void main(String[] args) {
        Application.launch();
    }

    @Override
    public void start(Stage stage) {
        Button play = new Button("play");
        play.setOnMouseClicked((MouseEvent t) -> {
            play.setText("loading");
            Level activeLevel = loadLevel(1);
            stage.setScene(toScene(activeLevel));
            // Implement the actual level loading method call
        });
        
        stage.setScene(new Scene(play));
        stage.show();
    }

    private Scene toScene(Level activeLevel) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
