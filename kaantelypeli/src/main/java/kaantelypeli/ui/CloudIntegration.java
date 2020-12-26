package kaantelypeli.ui;

import javafx.scene.control.ChoiceDialog;
import javafx.stage.Stage;

import java.util.Optional;

import static kaantelypeli.utils.FXUtils.selector;
import static kaantelypeli.utils.FileOperations.loadLevel;


public class CloudIntegration {
    public static final String FILESERVER =  "http://xylix.fi/levels";

    private void cloudMenu(Stage target) {
        ChoiceDialog<String> choice = selector(FILESERVER, target);
        Optional<String> result = choice.showAndWait();
        result.ifPresent(input ->  target.setScene(loadLevel(FILESERVER + '/' + input).toScene(() -> cloudMenu(target))));
    }
}
