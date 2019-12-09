package kaantelypeli.engine;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

public class LevelTest {
    @Test
    public void loadLevelTest() {
        assertEquals(Level.loadLevel(0).getClass(), Level.class);
    }
    
    @Test
    public void jsonTest() {
        ArrayList<Entity> entities = new ArrayList<>();
        Entity e = new Entity("player", 0, 0);
        Entity e2 = new Entity("wall", 4, 0);
        entities.add(e);
        entities.add(e2);
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Entity.class, new EntityAdapter());
        Gson gson = builder.create();
        String json = gson.toJson(entities);
        assertEquals("[\"{player,0.0,0.0}\",\"{wall,4.0,0.0}\"]", json);
    }
    
    
}
