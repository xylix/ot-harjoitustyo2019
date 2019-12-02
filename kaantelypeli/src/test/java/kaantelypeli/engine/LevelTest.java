package kaantelypeli.engine;

import org.junit.Test;
import static org.junit.Assert.*;

public class LevelTest {
    @Test
    public void loadLevelTest() {
        assertEquals(Level.loadLevel(1).getClass(), Level.class);
    }
    
    
}
