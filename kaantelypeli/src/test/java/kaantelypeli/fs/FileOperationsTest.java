package kaantelypeli.fs;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.util.concurrent.TimeoutException;
import kaantelypeli.engine.Entity;
import kaantelypeli.engine.Properties;
import static kaantelypeli.fs.FileOperations.loadProperties;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;

public class FileOperationsTest {
    @Before
    public void setUp() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
    }
    
    @Test
    public void entityFromJsonTest() {
        JsonElement json = JsonParser.parseString("{\"type\": \"player\",\"x\": 0.0,\"y\": 0.0,\"actionMap\":{\"lava\":\"loss\",\"victory\":\"victory\"},\"width\":14,\"height\":14,\"passable\":\"true\",\"movable\":\"true\"}");
        Gson gson = new Gson();
        Entity test = (gson.fromJson(json, Entity.class));
        Entity e = new Entity("player", 0, 0);
        assertEquals(e, test);
    }
    
    @Test
    public void loadPropertiesTest() {
        Properties p = loadProperties("player");
        JsonElement json = JsonParser.parseString("{\"type\": \"player\",\"actionMap\":{\"lava\":\"loss\",\"victory\":\"victory\"},\"width\":14,\"height\":14,\"movable\":\"true\",\"passable\":\"true\"}");
        Gson gson = new Gson();
        Properties p2 = gson.fromJson(json, Properties.class);
        assertEquals(gson.toJson(p), gson.toJson(p2));
    }
}
