package kaantelypeli.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import kaantelypeli.engine.Level;
import kaantelypeli.engine.Properties;
import kong.unirest.Unirest;
import org.tinylog.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import static kaantelypeli.ui.Game.SCALE;

/**
 * Static methods for file reading and JSON conversion.
 */
public class FileOperations {
    private FileOperations() {
        throw new IllegalStateException("Utility class");
    }
    /**
     * Load level JSON from file and parse it into a Level.
     * @param level Name of level to load
     * @return generated level
     */
    public static Level loadLevel(String level) {
        JsonElement json = loadJson("levels/" + level + ".json");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.fromJson(json, Level.class);
    }

    /**
     * Load level JSON from URL as a Level object.
     * @param url URL of the wanted level on a file server
     * @return Downloaded and parsed Level
     */
    public static Level downloadLevel(String url) {
        Logger.trace("GETing: " + url);
        return Unirest.get(url).asObject(Level.class).getBody();
    }


    /**
     * Loads Properties from file and parse it into a Properties instance.
     * @param filename name of entity properties to load
     * @return generated properties
     */
    public static Properties loadProperties(String filename) {
        JsonElement json = loadJson("entities/" + filename + ".json");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.fromJson(json, Properties.class);
    }
    
    /**
     * Loads JSON from a file containing valid JSON.
     * @param filename Path of file to load.
     * @return JsonElement containing parsed contents
     */
    public static JsonElement loadJson(String filename) {
        InputStream path = FileOperations.class.getClassLoader().getResourceAsStream(filename);
        if (path == null) {
            Logger.error("Entity: " + filename + " not found.");
        }
        try (InputStreamReader fr = new InputStreamReader(path)) {
            return JsonParser.parseReader(fr);
        } catch (IOException e) {
            Logger.error(e);
            return null;
        }
    }
    
    /**
     * Loads a sprite from a Filename.
     * @param filename name of sprite file to load.
     * @return a JavaFX Paint generated from loaded sprite or a green fill if not found.
     */
    public static Paint loadSprite(String filename) {
        URL spriteUrl = FileOperations.class.getClassLoader().getResource("sprites/" + filename);
        if (spriteUrl != null) {
            Image sprite = new Image(spriteUrl.toString());
            return new ImagePattern(sprite, 0, 0, 16 * SCALE, 16 * SCALE, false);
        } else {
            Logger.error("No sprite named: '" + filename + "' found");
            return Color.GREEN;
        }
    }
}
