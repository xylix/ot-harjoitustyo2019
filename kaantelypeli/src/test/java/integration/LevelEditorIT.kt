package integration

import javafx.scene.input.KeyCode
import javafx.scene.input.MouseButton
import kaantelypeli.ui.Game
import org.hamcrest.core.StringContains
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.contrib.java.lang.system.SystemOutRule
import org.junit.rules.TemporaryFolder
import org.testfx.api.FxToolkit
import org.testfx.framework.junit.ApplicationTest
import java.util.concurrent.TimeoutException
import kotlin.jvm.Throws

class LevelEditorIT : ApplicationTest() {
    @Rule
    val systemOutRule = SystemOutRule().muteForSuccessfulTests().enableLog()

    @Rule
    var folder = TemporaryFolder()

    @Before
    @Throws(TimeoutException::class)
    fun setUp() {
        FxToolkit.registerPrimaryStage()
        FxToolkit.setupApplication(Game::class.java)
    }

    @Test
    fun addTile() {
        openFirstLevel()
        systemOutRule.clearLog()
        spawnTile()
        push(KeyCode.ENTER)
        Assert.assertThat(
            systemOutRule.log,
            StringContains.containsString("TRACE: {\"type\":\"wall\"")
        )
        Assert.assertThat(
            systemOutRule.log,
            StringContains.containsString("\"actionMap\":{},\"width\":16,\"height\":16,")
        )
    }

    @Test
    fun cancelAddingTile() {
        openFirstLevel()
        systemOutRule.clearLog()
        clickOn(MouseButton.PRIMARY)
        push(KeyCode.ESCAPE)
        Assert.assertEquals("", systemOutRule.log)
    }

    @Test
    fun addWideTile() {
        openFirstLevel()
        systemOutRule.clearLog()
        spawnTile()
        // Set width and height
        push(KeyCode.TAB)
        push(KeyCode.DIGIT3)
        push(KeyCode.DIGIT2)
        push(KeyCode.TAB)
        push(KeyCode.DIGIT3)
        push(KeyCode.DIGIT2)
        push(KeyCode.ENTER)
        Assert.assertThat(
            systemOutRule.log, StringContains.containsString(
                "TRACE: {\"type\":\"wall\""
            )
        )
        Assert.assertThat(
            systemOutRule.log,
            StringContains.containsString(",\"width\":32,\"height\":32")
        )
    }

    fun spawnTile() {
        clickOn(MouseButton.PRIMARY)
        // Navigate to type selector.
        push(KeyCode.TAB)
        push(KeyCode.SPACE)
        push(KeyCode.SPACE)
    }

    @Test
    fun levelUpload() {
        openFirstLevel()
        push(KeyCode.TAB)
        push(KeyCode.TAB)
        push(KeyCode.SPACE)
        sleep(200)
        Assert.assertThat(
            systemOutRule.log,
            StringContains.containsString("TRACE: POSTing to " + Game.FILESERVER + "/levels/")
        )
        Assert.assertThat(
            systemOutRule.log,
            StringContains.containsString("TRACE: kong.unirest.EmptyResponse")
        )
    }

    @Test
    fun saveLevel() {
        openFirstLevel()
        push(KeyCode.TAB)
        push(KeyCode.SPACE)
        sleep(200)
        push(KeyCode.E)
        push(KeyCode.ENTER)
        Assert.assertThat(
            systemOutRule.log,
            StringContains.containsString("TRACE: Saving file to")
        )
    }

    @Test
    fun cancelledSave() {
        openFirstLevel()
        push(KeyCode.TAB)
        push(KeyCode.SPACE)
        sleep(200)
        push(KeyCode.ESCAPE)
        Assert.assertThat(
            systemOutRule.log,
            StringContains.containsString("Save dialog cancelled")
        )
    }

    fun openFirstLevel() {
        clickOn("#editor")
        push(KeyCode.DOWN)
        push(KeyCode.ENTER)
    }
}
