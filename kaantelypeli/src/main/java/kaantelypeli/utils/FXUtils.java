package kaantelypeli.utils;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.stage.Stage;
import kong.unirest.Unirest;

import java.util.List;

public class FXUtils {
    private FXUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Create a JavaFX button
     * @param name Name and Id for the button
     * @param action Action to call on button press
     * @return Button with the given options
     */
    public static Button button(String name, EventHandler<ActionEvent> action) {
        Button button = new Button(name);
        button.setOnAction(action);
        button.setId(name);
        return button;
    }

    /**
     * Create a list selector for a JSON list retrieved from URL
     * @param url list URL
     * @param stage stage to parent this choice dialog
     * @return ChoiceDialog with the given options
     */
    public static ChoiceDialog<String> selector(String url, Stage stage) {
        ChoiceDialog<String> choice = new ChoiceDialog<>();
        choice.initOwner(stage);
        choice.setHeaderText("Level to load");
        choice.setGraphic(null);
        choice.setTitle(null);
        List<Object> options = Unirest.get(url).asJson().getBody().getArray().toList();
        for (Object i : options) {
            choice.getItems().add(url + '/' + i);
        }
        return choice;
    }
}
