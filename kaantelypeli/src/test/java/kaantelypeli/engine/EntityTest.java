package kaantelypeli.engine;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.util.concurrent.TimeoutException;
import javafx.stage.Stage;
import static kaantelypeli.fs.FileOperations.entityFromJson;
import kaantelypeli.ui.Game;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.testfx.api.FxToolkit;

public class EntityTest {
    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();
    private Stage stage;
    
    @Before
    public void setUp() throws TimeoutException {
        stage = FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(Game.class);
    }
    
    @Test
    public void illegalMovementTest() {
        Entity test = new Entity("player", 0, 0);
        double x = test.hitbox.getX() + test.hitbox.getTranslateX();
        double y = test.hitbox.getY() + test.hitbox.getTranslateY();
        test.move(4);
        assertEquals(x, test.getActualX(), 0);
        assertEquals(y, test.getActualY(), 0);
        assertEquals("Illegal movement call" + System.lineSeparator(), systemOutRule.getLog());
    }
    
    @Test
    public void spriteNotFoundTest() {
        Entity test = new Entity("test", 0, 0);
        assertEquals("Entity type not supported." + System.lineSeparator() + 
            "No sprite named: 'test.png' found" + System.lineSeparator(), systemOutRule.getLog());
    }
    
    @Test
    public void toJsonTest() {
        Entity e = new Entity("player", 0, 0);
        assertEquals("{\"type\":\"player\",\"xCoord\":0,\"yCoord\":0,\"movable\":true,\"passable\":true}", e.toJson());
    }
}
