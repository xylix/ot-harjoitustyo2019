package kaantelypeli;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
 

public class Game extends Application {
    public static void main(String[] args) {
        Application.launch();
    }

    @Override
    public void start(Stage stage) {
        Button play = new Button("play");
        play.setOnMouseClicked((MouseEvent t) -> {
            play.setText("loading");
        });
        
        stage.setScene(new Scene(play));
        stage.show();
        
        
    }
    
}
