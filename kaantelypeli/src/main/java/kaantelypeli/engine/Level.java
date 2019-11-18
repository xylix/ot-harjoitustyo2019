package kaantelypeli;

import java.util.ArrayList;
import java.util.Collection;
import kaantelypeli.engine.Entity;

/**
 *
 * @author xylix
 */
public class Level {
    Collection<Entity> entities;
    
    public Level() { 
        entities = new ArrayList<>();
    }
    
    public static Level loadLevel(int level) {
        throw new UnsupportedOperationException();
    }
}
