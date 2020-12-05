package kaantelypeli.engine

import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.contrib.java.lang.system.SystemErrRule
import org.testfx.api.FxToolkit
import java.util.concurrent.TimeoutException
import kotlin.jvm.Throws

class EntityTest {
    @Rule
    val systemErrRule = SystemErrRule().muteForSuccessfulTests().enableLog()

    @Before
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
        Assert.assertEquals(x, test.actualX, 0.0)
        Assert.assertEquals(y, test.actualY, 0.0)
        Assert.assertTrue(systemErrRule.log.contains("Illegal movement call"))
    }

    @Test(expected = NullPointerException::class)
    fun entityNotFound() {
        Entity("test", 0, 0)
        Assert.assertTrue(systemErrRule.log.contains("Entity: test not found."))
    }

    @get:Test
    val json: Unit
        get() {
            val e = Entity("player", 0, 0)
            Assert.assertEquals(
                "{\"type\":\"player\",\"x\":0,\"y\":0,\"actionMap\":{\"lava\":\"loss\",\"victory\":\"victory\"},\"width\":14,\"height\":14,\"movable\":true,\"passable\":true}",
                e.json
            )
        }

    @Test
    fun hashCodeTest() {
        val e = Entity("player", 0, 0)
        val e2 = Entity("player", 0, 0)
        Assert.assertEquals(e.hashCode().toLong(), e2.hashCode().toLong())
        val e3 = Entity("wall", 0, 0)
        Assert.assertNotEquals(e.hashCode().toLong(), e3.hashCode().toLong())
    }

    @Test
    fun equalsSelf() {
        val e = Entity("player", 0, 0)
        Assert.assertTrue(e.equals(e))
    }

    @Test
    fun equalsNull() {
        val e = Entity("player", 0, 0)
        Assert.assertFalse(e.equals(null))
    }

    @Test
    fun equalsObject() {
        val e = Entity("player", 0, 0)
        Assert.assertFalse(e.equals(Any()))
    }

    @get:Test
    val hitbox: Unit
        get() {
            val e = Entity("player", 0, 0)
            val hitbox = e.hitbox
            Assert.assertEquals(0.0, hitbox!!.x)
            Assert.assertEquals(0.0, hitbox!!.y)
        }
}
