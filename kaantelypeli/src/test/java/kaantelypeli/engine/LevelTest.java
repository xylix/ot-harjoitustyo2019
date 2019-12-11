package kaantelypeli.engine;

import java.util.concurrent.TimeoutException;
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
        assertEquals(Level.loadLevel(0).getClass(), Level.class);
    }
    
    @Test
    public void toJsonTest() {
        Level l = Level.loadLevel(-1);
        assertEquals("[{\"type\":\"player\",\"xCoord\":0,\"yCoord\":16,\"movable\":true,\"passable\":true},{\"type\":\"victory\",\"xCoord\":0,\"yCoord\":24,\"movable\":false,\"passable\":true}]", l.toJson());
    }
    
    
}
