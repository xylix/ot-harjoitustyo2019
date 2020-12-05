package kaantelypeli.utils

import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import javafx.scene.image.Image
import javafx.scene.paint.Color
import javafx.scene.paint.ImagePattern
import javafx.scene.paint.Paint
import kaantelypeli.engine.Level
import kaantelypeli.engine.Properties
import kaantelypeli.ui.SCALE
import kong.unirest.Unirest
import org.tinylog.kotlin.Logger
import java.io.File
import java.io.IOException
import java.io.InputStreamReader

/**
 * Static methods for file reading and JSON conversion.
 */

/**
 * Load level JSON from file and parse it into a Level. If String is url load from address.
 * @param level Name of level to load
 * @return generated level
 */
fun loadLevel(level: String): Level {
    // Check if level is a URL
    return if (level.contains("http")) {
        Unirest.get(level).asObject(Level::class.java).getBody()
    } else {
        val json = loadJson("levels/$level.json")
        val gson = GsonBuilder().setPrettyPrinting().create()
        gson.fromJson(json, Level::class.java)
    }
}

/**
 * Loads Properties from file and parse it into a Properties instance.
 * @param filename name of entity properties to load
 * @return generated properties
 */
fun loadProperties(filename: String): Properties {
    val json = loadJson("entities/$filename.json")
    val gson = GsonBuilder().setPrettyPrinting().create()
    return gson.fromJson(json, Properties::class.java)
}

/**
 * Loads JSON from a file containing valid JSON.
 * @param filename Path of file to load.
 * @return JsonElement containing parsed contents
 */
fun loadJson(filename: String): JsonElement? {
    val path = object {}.javaClass.classLoader.getResourceAsStream(filename)
    try {
        InputStreamReader(path ?: throw Error("Entity: $filename not found")).use { fr -> return JsonParser.parseReader(fr) }
    } catch (e: IOException) {
        Logger.error(e)
        return null
    }
}

/**
 * Loads a sprite from a Filename.
 * @param filename name of sprite file to load.
 * @return a JavaFX Paint generated from loaded sprite or a green fill if not found.
 */
fun loadSprite(filename: String): Paint {
    val spriteUrl =
        object {}.javaClass.classLoader.getResource("sprites/$filename")
    return if (spriteUrl != null) {
        val sprite = Image(spriteUrl.toString())
        IntImagePattern(sprite, 0, 0, 16 * SCALE, 16 * SCALE, false)
    } else {
        Logger.error("No sprite from PATH: 'sprites/{}' found", filename)
        Color.GREEN
    }
}


/**
 * Upload level to target url.
 * @param target URL of fileserver to upload to
 * @param l Level to upload
 * @return Parsed HTTP response converted into a string
 */
fun uploadLevel(target: String?, l: Level): String {
    return Unirest.post(target)
        .header("Content-Type", "application/json")
        .body(l.json)
        .connectTimeout(4000)
        .asEmpty().toString()
}

/**
 * Get a File pointing to `resource` using parameter classes loader. Produces a NullPointerException if
 * resource can't be found.
 * @param resource Name of resource to load
 * @param clazz Class to get classloader from (usually the class where getClassResource gets called)
 * @return File pointing to the resource
 */
fun getClassResource(resource: String?, clazz: Class<*>): File {
    return File(clazz.classLoader.getResource(resource).file)
}
