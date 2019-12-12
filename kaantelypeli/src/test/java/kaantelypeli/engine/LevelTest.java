package kaantelypeli.engine;

import java.util.concurrent.TimeoutException;
import static kaantelypeli.fs.FileOperations.loadLevel;
import kaantelypeli.ui.Game;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.testfx.api.FxToolkit;

public class LevelTest {
    @Before
    public void setUp() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(Game.class);
    }
    
    @Test
    public void loadLevelTest() {
        assertEquals(loadLevel(0).getClass(), Level.class);
    }
    
    @Test
    public void toJsonTest() {
        Level l = loadLevel(-1);
        assertEquals("{\"entities\":[{\"type\":\"player\",\"xCoord\":0,\"yCoord\":16,\"movable\":true,\"passable\":true},{\"type\":\"victory\",\"xCoord\":0,\"yCoord\":24,\"movable\":false,\"passable\":true}],\"gravity\":0,\"id\":-1,\"won\":false,\"lost\":false}", l.toJson());
    }
    
    @Test
    public void changeGravityTest() {
        Level l = loadLevel(-1);
        int old = l.gravity;
        l.changeGravity(4);
        assertEquals(old + 4, l.gravity);
    }
    
}
