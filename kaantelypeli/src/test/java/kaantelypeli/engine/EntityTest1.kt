package kaantelypeli.engine

import org.testfx.api.FxToolkit
import java.util.concurrent.TimeoutException
import kotlin.jvm.Throws
import kotlin.test.Test
import kotlin.test.BeforeTest
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals

class EntityTest {
    @BeforeTest
    @Throws(TimeoutException::class)
    fun setUp() {
        FxToolkit.registerPrimaryStage()
    }

    @Test
    fun illegalMovement() {
        val test = Entity("player", 0, 0)
        val x = test.hitbox!!.x + test.hitbox!!.translateX
        val y = test.hitbox!!.y + test.hitbox!!.translateY
        test.move(4)
        assertEquals(x, test.actualX)
        assertEquals(y, test.actualY)
        // assertTrue(systemErrRule.log.contains("Illegal movement call"))
    }

    @Test(expected = NullPointerException::class)
    fun entityNotFound() {
        Entity("test", 0, 0)
        // assertTrue(systemErrRule.log.contains("Entity: test not found."))
    }

    @get:Test
    val json: Unit
        get() {
            val e = Entity("player", 0, 0)
            assertEquals(
                "{\"type\":\"player\",\"x\":0,\"y\":0,\"actionMap\":{\"lava\":\"loss\",\"victory\":\"victory\"},\"width\":14,\"height\":14,\"movable\":true,\"passable\":true}",
                e.json
            )
        }

    @Test
    fun hashCodeTest() {
        val e = Entity("player", 0, 0)
        val e2 = Entity("player", 0, 0)
        assertEquals(e.hashCode().toLong(), e2.hashCode().toLong())
        val e3 = Entity("wall", 0, 0)
        assertNotEquals(e.hashCode().toLong(), e3.hashCode().toLong())
    }

    @Test
    fun equalsSelf() {
        val e = Entity("player", 0, 0)
        assertTrue(e.equals(e))
    }

    @Test
    fun equalsNull() {
        val e = Entity("player", 0, 0)
        assertFalse(e.equals(null))
    }

    @Test
    fun equalsObject() {
        val e = Entity("player", 0, 0)
        assertFalse(e.equals(Any()))
    }

    @get:Test
    val hitbox: Unit
        get() {
            val e = Entity("player", 0, 0)
            val hitbox = e.hitbox
            assertEquals(0.0, hitbox!!.x)
            assertEquals(0.0, hitbox!!.y)
        }
}
