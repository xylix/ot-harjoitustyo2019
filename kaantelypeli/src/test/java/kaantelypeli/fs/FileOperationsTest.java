package kaantelypeli.fs;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import kaantelypeli.engine.Entity;
import static kaantelypeli.fs.FileOperations.entityFromJson;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author xylix
 */
public class FileOperationsTest {
        
    @Test
    public void entityFromJsonTest() {
        JsonElement json = JsonParser.parseString("{\"type\": \"player\",\"x\": 0.0,\"y\": 0.0,\"width\":16,\"height\":16,\"passable\":\"true\",\"movable\":\"true\"}");
        Entity test = entityFromJson(json);
        Entity e = new Entity("player", 0, 0, 16, 16);
        assertEquals(e, test);
    }
}
