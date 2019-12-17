package kaantelypeli.ui;

import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import java.util.concurrent.TimeoutException;

import static org.junit.Assert.assertEquals;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

public class LevelEditorTest extends ApplicationTest {
    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().muteForSuccessfulTests().enableLog();

    @Before
    public void setUp() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(Game.class);
    }

    @Test
    public void openEditor() {
        verifyThat("#editor", hasText("Level editor"));
        clickOn("#editor");
    }

    @Test
    public void openLevel() {
        openFirstLevel();
        assertEquals("INFO: Opened a copy of level 1 in editor" + System.lineSeparator(), systemOutRule.getLog());

    }

    @Test
    public void addTile() {
        openFirstLevel();
        clickOn(MouseButton.PRIMARY);
        // Navigate to type selector.
        press(KeyCode.SHIFT);
        push(KeyCode.TAB);
        push(KeyCode.TAB);
        push(KeyCode.TAB);
        push(KeyCode.TAB);
        release(KeyCode.SHIFT);
        push(KeyCode.SPACE);
        // Confirm type and close dialog.
        push(KeyCode.ENTER);
    }

    @Test
    public void addWideTile() {
        openFirstLevel();
        systemOutRule.clearLog();
        clickOn(MouseButton.PRIMARY);

        // Navigate to type selector
        press(KeyCode.SHIFT);
        push(KeyCode.TAB);
        push(KeyCode.TAB);
        push(KeyCode.TAB);
        push(KeyCode.TAB);
        release(KeyCode.SHIFT);
        push(KeyCode.SPACE);
        push(KeyCode.SPACE);

        // Set width and height
        push(KeyCode.TAB);
        push(KeyCode.DIGIT3);
        push(KeyCode.DIGIT2);
        push(KeyCode.TAB);
        push(KeyCode.DIGIT3);
        push(KeyCode.DIGIT2);

        push(KeyCode.ENTER);
        assertEquals("INFO: {\"type\":\"door\",\"x\":32,\"y\":64,\"actionMap\":{},\"width\":64,\"height\":64,\"movable\":false,\"passable\":false}"
                 + System.lineSeparator(), systemOutRule.getLog());
    }

    @Test
    public void saveLevel() {

    }

    public void openFirstLevel() {
        clickOn("#editor");
        push(KeyCode.DOWN);
        push(KeyCode.ENTER);
    }
}
