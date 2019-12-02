package kaantelypeli.ui;

import java.util.concurrent.TimeoutException;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.number.IsCloseTo.closeTo;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

public class IntegrationTest extends ApplicationTest {
    Stage stage;
    
    @Before
    public void setUp() throws TimeoutException {
        stage = FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(Game.class);
    }
    
    @Test
    public void CollisionTest() {
        clickOn(".button1");
        // Find player node with css selector
        Rectangle player = (Rectangle) stage.getScene().getRoot().lookup("#player");
        push(KeyCode.LEFT);
        push(KeyCode.LEFT);
        sleep(300);
        assertThat(player.getX() + player.getTranslateX(), closeTo(112, 96));
        assertThat(player.getY() + player.getTranslateY(), closeTo(112, 96));
        
        
    }
}
