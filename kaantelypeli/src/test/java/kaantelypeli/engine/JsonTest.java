package kaantelypeli.engine;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author xylix
 */
public class JsonTest {
    @Test
    public void JsonTest() {
        Entity e = new Entity("player", 0, 0);
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Entity.class, new EntityAdapter());
        Gson gson = builder.create();
        String json = gson.toJson(e);
        assertEquals("\"{player,0.0,0.0}\"", json);
    }
    
    @Before
    public void setUp() {
    }
    
}
