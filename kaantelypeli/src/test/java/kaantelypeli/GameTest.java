package kaantelypeli;

import java.util.concurrent.TimeoutException;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Test;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

public class GameTest extends ApplicationTest {
    Stage stage;
    
    @Before
    public void setUp() throws TimeoutException {
        stage = FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(Game.class);
    }
    
    @Test
    public void startButtonTest()  {      
        verifyThat(".button", hasText("play"));
    }
    
    @Test
    public void gameStartTest() {
        clickOn(".button");
        press(KeyCode.LEFT);
        // somehow verify that correct things happen after pressing left
        
    }
}
