package kaantelypeli.fs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import kaantelypeli.engine.Entity;
import kaantelypeli.engine.Level;

/**
 * Static methods for file reading and JSON conversion.
 */
public class FileOperations {
    private FileOperations() {
        throw new IllegalStateException("Utility class");
    }
    /**
     * Load level JSON from file and parse it into a Level.
     * @param levelId Index number of level to load
     * @return generated level
     */
    public static Level loadLevel(int levelId) {
        JsonElement json = loadJson("levels/level" + levelId + ".json");
        return levelFromJson(json);
    }
    
    /**
     * Load Entity JSON from file and parse it into an Entity.
     * @param filename Path of file to load.
     * @return generated entity
     */
    public static Entity loadEntity(String filename) {
        JsonElement json = loadJson(filename);
        return entityFromJson(json);
    }
    
    /**
     * Loads JSON from a file containing valid JSON.
     * @param filename Path of file to load.
     * @return JsonElement containing parsed contents
     */
    public static JsonElement loadJson(String filename) {
        InputStream path = FileOperations.class.getClassLoader().getResourceAsStream(filename);
        try (InputStreamReader fr = new InputStreamReader(path)) {
            return JsonParser.parseReader(fr);
        } catch (IOException e) {
            System.out.println(e);
            return null;
        }
    }
    
    /**
     * Converts JSON representation of a level to a new Level.
     * @param json Valid level JSON. (Must contain a list of entities).
     * @return Generated Level
     */
    public static Level levelFromJson(JsonElement json) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.fromJson(json, Level.class);
    }
    
    /**
     * Converts JSON representation of an entity to a new Entity.
     * @param json Valid entity JSON.
     * @return Generated entity.
     */
    public static Entity entityFromJson(JsonElement json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Entity.class);
    }

    /**
     * Loads a sprite from a Filename.
     * @param filename name of sprite file to load.
     * @return a JavaFX Paint generated from loaded sprite or a green fill if not found.
     */
    public static Paint loadSprite(String filename) {
        URL spriteUrl = FileOperations.class.getClassLoader().getResource("sprites/" + filename + ".png");
        if (spriteUrl != null) {
            Image sprite = new Image(spriteUrl.toString());
            return new ImagePattern(sprite, 0, 0, 16, 16, false);
        } else {
            System.out.println("No sprite named: '" + filename + "' found");
            return (Color.GREEN);
        }
    }
}
