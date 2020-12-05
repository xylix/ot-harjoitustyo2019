package kaantelypeli.ui

import javafx.scene.input.KeyCode
import kaantelypeli.ui.Game
import org.hamcrest.core.StringContains
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.contrib.java.lang.system.SystemOutRule
import org.testfx.api.FxToolkit
import org.testfx.framework.junit.ApplicationTest
import java.util.concurrent.TimeoutException
import kotlin.jvm.Throws

class LevelEditorTest : ApplicationTest() {
    @Rule
    val systemOutRule = SystemOutRule().muteForSuccessfulTests().enableLog()

    @Before
    @Throws(TimeoutException::class)
    fun setUp() {
        FxToolkit.registerPrimaryStage()
        FxToolkit.setupApplication(Game::class.java)
    }

    @Test
    fun openLevel() {
        clickOn("#editor")
        push(KeyCode.DOWN)
        push(KeyCode.ENTER)
        Assert.assertThat(
            systemOutRule.log,
            StringContains.containsString("TRACE: Opened a copy of level 1 in editor")
        )
    }
}
