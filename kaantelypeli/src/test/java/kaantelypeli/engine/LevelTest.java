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
    public void jsonTest() {
        Level l = Level.loadLevel(-1);
        assertEquals("[\"{player,0.0,16.0}\",\"{victory,0.0,24.0}\"]", l.toJson());
    }
    
    
}
