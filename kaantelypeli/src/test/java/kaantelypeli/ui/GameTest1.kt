package kaantelypeli.ui

import kaantelypeli.ui.Game
import org.junit.Rule
import org.junit.contrib.java.lang.system.SystemOutRule
import org.testfx.api.FxAssert
import org.testfx.api.FxToolkit
import org.testfx.framework.junit.ApplicationTest
import org.testfx.matcher.control.LabeledMatchers
import java.util.concurrent.TimeoutException
import kotlin.jvm.Throws
import kotlin.test.Test
import kotlin.test.BeforeTest
import kotlin.test.assertEquals

class GameTest : ApplicationTest() {
    @Rule
    val systemOutRule = SystemOutRule().muteForSuccessfulTests().enableLog()

    @BeforeTest
    @Throws(TimeoutException::class)
    fun setUp() {
        FxToolkit.registerPrimaryStage()
        FxToolkit.setupApplication(Game::class.java)
    }

    @Test
    fun openEditor() {
        FxAssert.verifyThat("#editor", LabeledMatchers.hasText("editor"))
        clickOn("#editor")
    }

    @Test
    fun startButtonTest() {
        FxAssert.verifyThat("#test0", LabeledMatchers.hasText("test0"))
        clickOn("#test0")
    }

    @Test
    fun victoryTest() {
        clickOn("#test1")
        assertEquals(
            "TRACE: victory\nYou're winner!\n",
            systemOutRule.logWithNormalizedLineSeparator
        )
    }
}
