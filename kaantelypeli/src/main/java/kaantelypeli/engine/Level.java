package kaantelypeli.engine;

import java.util.ArrayList;
import java.util.Collection;
import javafx.geometry.Point2D;

/**
 *
 * @author xylix
 */
public class Level {
    public Collection<Entity> entities;
    public Entity player;
    
    public Level() { 
        entities = new ArrayList<>();
    }
    
    public static Level loadLevel(int levelIndex) {
        switch (levelIndex) {
            case 1:
                Level level = new Level();
                Entity player = new Entity("player", new Point2D(0, 0));
                level.player = player;
                level.entities.add(player);
                level.entities.add(new Entity("wall", new Point2D(48, 48)));
                return level;
            case 2:
                ;
        }
        return new Level();
    }
}
