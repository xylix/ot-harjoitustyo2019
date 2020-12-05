package kaantelypeli.utils

import com.google.gson.Gson
import com.google.gson.JsonParser
import kaantelypeli.engine.Entity
import kaantelypeli.engine.Properties
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.testfx.api.FxToolkit
import java.util.concurrent.TimeoutException
import kotlin.jvm.Throws

class FileOperationsTest {
    @Before
    @Throws(TimeoutException::class)
    fun setUp() {
        FxToolkit.registerPrimaryStage()
    }

    @Test
    fun entityFromJsonTest() {
        val json =
            JsonParser.parseString("{\"type\": \"player\",\"x\": 0.0,\"y\": 0.0,\"actionMap\":{\"lava\":\"loss\",\"victory\":\"victory\"},\"width\":14,\"height\":14,\"passable\":\"true\",\"movable\":\"true\"}")
        val gson = Gson()
        val test = gson.fromJson(json, Entity::class.java)
        val e = Entity("player", 0, 0)
        Assert.assertEquals(e, test)
    }

    @Test
    fun loadPropertiesTest() {
        val p = loadProperties("player")
        val json =
            JsonParser.parseString("{\"type\": \"player\",\"actionMap\":{\"lava\":\"loss\",\"victory\":\"victory\"},\"width\":14,\"height\":14,\"movable\":\"true\",\"passable\":\"true\", \"graphics\":\"player.gif\"}")
        val gson = Gson()
        val p2 = gson.fromJson(json, Properties::class.java)
        Assert.assertEquals(gson.toJson(p), gson.toJson(p2))
    }
}
