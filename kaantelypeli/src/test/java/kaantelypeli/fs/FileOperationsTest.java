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
    public void fromJsonTest() {
        JsonElement json = JsonParser.parseString("{\"type\": \"player\",\"xCoord\": 0.0,\"yCoord\": 0.0,\"passable\":\"true\",\"movable\":\"true\"}");
        Entity test = entityFromJson(json);
        Entity e = new Entity("player", 0, 0);
        assertEquals(e, test);
    }
}
