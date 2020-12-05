package kaantelypeli.engine

import kaantelypeli.ui.Game
import kaantelypeli.utils.loadLevel
import org.testfx.api.FxToolkit
import java.util.*
import java.util.concurrent.TimeoutException
import kotlin.jvm.Throws
import kotlin.test.Test
import kotlin.test.BeforeTest
import kotlin.test.assertEquals

class LevelTest {
    @BeforeTest
    @Throws(TimeoutException::class)
    fun setUp() {
        FxToolkit.registerPrimaryStage()
        FxToolkit.setupApplication(Game::class.java)
    }

    @Test
    fun loadLevelTest() {
        val l: Level = loadLevel("test0")
        assertEquals(l.javaClass, Level::class.java)
    }

    @Test
    fun negativeOneToJsonTest() {
        val l: Level = loadLevel("test1")
        assertEquals(
            Level(
                Arrays.asList(
                    Entity("player", 0, 16),
                    Entity("victory", 0, 24)
                )
            ).json, l.json
        )
    }

    @Test
    fun changeGravityTest() {
        val l: Level = loadLevel("test1")
        val old = l.gravity
        l.changeGravity(4)
        assertEquals(old + 4.toLong(), l.gravity.toLong())
    }
}
