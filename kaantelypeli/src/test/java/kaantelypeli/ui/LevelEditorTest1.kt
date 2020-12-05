package kaantelypeli.ui

import javafx.scene.input.KeyCode
import kaantelypeli.ui.Game
import org.junit.Rule
import org.junit.contrib.java.lang.system.SystemOutRule
import org.testfx.api.FxToolkit
import org.testfx.framework.junit.ApplicationTest
import java.util.concurrent.TimeoutException
import kotlin.jvm.Throws

import kotlin.test.Test
import kotlin.test.BeforeTest
import kotlin.test.assertTrue

class LevelEditorTest : ApplicationTest() {
    // @JvmField @Rule val systemOutRule = SystemOutRule().muteForSuccessfulTests().enableLog()

    @BeforeTest
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
        // assertTrue(systemOutRule.log.contains("TRACE: Opened a copy of level 1 in editor"))
    }
}
