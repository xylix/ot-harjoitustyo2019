package kaantelypeli.ui;

import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import java.util.concurrent.TimeoutException;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

public class LevelEditorTest extends ApplicationTest {
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
        
    }

    @Test
    public void addTile() {
        openFirstLevel();
        clickOn(MouseButton.PRIMARY);
        press(KeyCode.SHIFT);
        push(KeyCode.TAB);
        push(KeyCode.TAB);
        push(KeyCode.TAB);
        push(KeyCode.TAB);
        release(KeyCode.SHIFT);
        push(KeyCode.SPACE);
        push(KeyCode.ENTER);

    }

    @Test
    public void tilePlacement() {

    }

    public void openFirstLevel() {
        clickOn("#editor");
        push(KeyCode.DOWN);
        push(KeyCode.ENTER);
    }
}
