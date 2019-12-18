package kaantelypeli.ui;

import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeoutException;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeTrue;

public class LevelEditorTest extends ApplicationTest {
    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().muteForSuccessfulTests().enableLog();
    @Before
    public void setUp() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(Game.class);
        openFirstLevel();
    }

    @Test
    public void openLevel() {
        assertEquals("TRACE: Opened a copy of level 1 in editor" + System.lineSeparator(), systemOutRule.getLog());

    }

    @Test
    public void addTile() {
        systemOutRule.clearLog();
        spawnTile();
        push(KeyCode.ENTER);
        assertEquals("TRACE: {\"type\":\"door\",\"x\":16,\"y\":32,\"actionMap\":{},\"width\":16,\"height\":16,\"movable\":false,\"passable\":false}"
                + System.lineSeparator(), systemOutRule.getLog());
    }

    @Test
    public void cancelAddingTile() {
        systemOutRule.clearLog();
        clickOn(MouseButton.PRIMARY);
        push(KeyCode.ESCAPE);
        assertEquals("", systemOutRule.getLog());
    }

    @Test
    public void addWideTile() {
        systemOutRule.clearLog();
        spawnTile();
        // Set width and height
        push(KeyCode.TAB);
        push(KeyCode.DIGIT3);
        push(KeyCode.DIGIT2);
        push(KeyCode.TAB);
        push(KeyCode.DIGIT3);
        push(KeyCode.DIGIT2);

        push(KeyCode.ENTER);
        assertEquals("TRACE: {\"type\":\"door\",\"x\":16,\"y\":32,\"actionMap\":{},\"width\":32,\"height\":32,\"movable\":false,\"passable\":false}"
                 + System.lineSeparator(), systemOutRule.getLog());
    }
    
    @Test
    public void saveLevel() {
        if (System.getProperty("testfx.headless", "false").equals("true")) {
            // Skip test if running headless, Monocle's FileChooser dialog is not implemented on headless environments
            assumeTrue(false);
        }
        push(KeyCode.SPACE);
        systemOutRule.clearLog();
        sleep(200);
        push(KeyCode.ENTER);

        try {
            URL level = LevelEditor.class.getClassLoader().getResource("levels/edited.json");
            Files.delete(Paths.get(level.getPath()));
        } catch (NullPointerException | IOException e) {
            e.printStackTrace();
        }
        assertThat(systemOutRule.getLog(), containsString("TRACE: Saving file to"));
    }

    @Test
    public void cancelledSave () {
        if (System.getProperty("testfx.headless", "false").equals("true")) {
            // Skip test if running headless, Monocle's FileChooser dialog is not implemented on headless environments
            assumeTrue(false);
        }
        push(KeyCode.SPACE);
        sleep(200);
        push(KeyCode.ESCAPE);
        assertThat(systemOutRule.getLog(), containsString("Save dialog cancelled"));
    }



    public void spawnTile() {
        clickOn(MouseButton.PRIMARY);
        // Navigate to type selector.
        press(KeyCode.SHIFT);
        push(KeyCode.TAB);
        push(KeyCode.TAB);
        push(KeyCode.TAB);
        push(KeyCode.TAB);
        release(KeyCode.SHIFT);
        push(KeyCode.SPACE);
        push(KeyCode.SPACE);
    }

    public void openFirstLevel() {
        clickOn("#editor");
        push(KeyCode.DOWN);
        push(KeyCode.ENTER);
    }
}
