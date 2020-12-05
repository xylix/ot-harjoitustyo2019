package kaantelypeli.engine

import kaantelypeli.ui.Game
import kaantelypeli.utils.loadLevel
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.testfx.api.FxToolkit
import java.util.*
import java.util.concurrent.TimeoutException
import kotlin.jvm.Throws

class LevelTest {
    @Before
    @Throws(TimeoutException::class)
    fun setUp() {
        FxToolkit.registerPrimaryStage()
        FxToolkit.setupApplication(Game::class.java)
    }

    @Test
    fun loadLevelTest() {
        val l: Level = loadLevel("test0")
        Assert.assertEquals(l.javaClass, Level::class.java)
    }

    @Test
    fun negativeOneToJsonTest() {
        val l: Level = loadLevel("test1")
        Assert.assertEquals(
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
        Assert.assertEquals(old + 4.toLong(), l.gravity.toLong())
    }
}
