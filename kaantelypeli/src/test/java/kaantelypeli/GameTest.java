package kaantelypeli;

import java.util.concurrent.TimeoutException;
import javafx.scene.input.MouseButton;
import javafx.scene.robot.Robot;
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
        // assert something that ensures click resolves
    }
    
    @Test
    public void startButtonClickTest() {
        clickOn(".button");
        verifyThat(".button", hasText("loading"));
    }
}
