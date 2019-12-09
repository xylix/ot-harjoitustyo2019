package kaantelypeli.engine;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;

/**
 * GSon TypeAdapter for class Entity.
 */
public class EntityAdapter extends TypeAdapter<Entity> {
    @Override
    public void write(JsonWriter writer, Entity e) throws IOException {
        if (e == null) {
            writer.nullValue();
        } else {
            String value = "{" + e.type + "," + e.getActualX() + "," + e.getActualY() + "}";
            writer.value(value);
        }
    }

    @Override
    public Entity read(JsonReader reader) throws IOException {
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull();
            return null;
        } else {
            String raw = reader.nextString();
            String[] parts = raw.split(",");
            String type = parts[0].substring(1);
            int x = Integer.parseInt(parts[1].substring(1));
            int y = Integer.parseInt(parts[2].substring(1));
            return new Entity(type, x, y);
        }
    }
    
}
