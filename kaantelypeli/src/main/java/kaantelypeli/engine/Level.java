package kaantelypeli;

import java.util.ArrayList;
import java.util.Collection;
import kaantelypeli.engine.Entity;

/**
 *
 * @author xylix
 */
public class Level {
    public Collection<Entity> entities;
    
    public Level() { 
        entities = new ArrayList<>();
    }
    
    public static Level loadLevel(int level) {
        switch (level) {
            case 1:
                return new Level();
            case 2:
                ;
        }
        return new Level();
    }
}
