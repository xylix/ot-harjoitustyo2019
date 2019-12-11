package kaantelypeli.fs;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.FileReader;
import java.io.IOException;
import kaantelypeli.engine.Entity;
import kaantelypeli.engine.Level;

/**
 * Static methods for file reading and JSON conversion.
 */
public class FileOperations {
    /**
     * Load level JSON from file and parse it into a Level.
     * @param filename Path of file to load.
     * @return generated level
     */
    public static Level loadLevel(String filename) {
        JsonElement json = loadJson(filename);
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
        try (FileReader fr = new FileReader(filename)) {
            JsonElement json = JsonParser.parseReader(fr);
            return json;
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
        Gson gson = new Gson();
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
}
