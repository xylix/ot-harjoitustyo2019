package kaantelypeli.ui;

import java.util.concurrent.TimeoutException;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.control.LabeledMatchers.hasText;


public class GameTest extends ApplicationTest {
    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();
    Stage stage;
    
    @Before
    public void setUp() throws TimeoutException {
        stage = FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(Game.class);
    }
    
    @Test
    public void startButtonTest()  {      
        verifyThat(".button1", hasText("level 1"));
        clickOn(".button1");
    }
    
    @Test
    public void victoryTest() {
        clickOn(".button0");
        sleep(200);
        assertEquals("You're winner!\n", systemOutRule.getLog());
    }
}
