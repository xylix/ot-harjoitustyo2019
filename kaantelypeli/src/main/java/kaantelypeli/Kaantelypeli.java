package kaantelypeli;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
 

public class Kaantelypeli extends Application {
    public static void main(String[] args) {
        Application.launch();
    }

    @Override
    public void start(Stage stage) {
        Button aloita = new Button("Aloita peli");
        stage.setScene(new Scene(aloita));
        stage.show();
    }
    
}
