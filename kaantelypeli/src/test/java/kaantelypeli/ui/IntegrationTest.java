package kaantelypeli.ui;

import java.util.concurrent.TimeoutException;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import kaantelypeli.engine.Entity;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.number.IsCloseTo.closeTo;
import org.junit.Rule;
import org.junit.contrib.java.lang.system.SystemOutRule;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

public class IntegrationTest extends ApplicationTest {
    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();
    Stage stage;

    @Test
    public void startButtonTest()  {      
        verifyThat(".button1", hasText("level 1"));
        clickOn(".button1");
    }
    
    @Test
    public void victoryTest() {
        clickOn(".button-1");
        sleep(500);
        assertEquals("You're winner!" + System.lineSeparator(), systemOutRule.getLog());
    }    
    @Before
    public void setUp() throws TimeoutException {
        stage = FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(Game.class);
    }
    
    @Test
    public void CollisionTest() {
        clickOn(".button1");
        // Find player node with css selector
        Entity player = (Entity) stage.getScene().getRoot().lookup("#player");
        push(KeyCode.LEFT);
        push(KeyCode.LEFT);
        sleep(300);
        assertThat(player.getActualX(), closeTo(112, 96));
        assertThat(player.getActualY(), closeTo(112, 96));
    }
}
