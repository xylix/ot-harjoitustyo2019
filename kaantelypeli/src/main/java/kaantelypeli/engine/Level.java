package kaantelypeli.engine;

import java.util.ArrayList;
import java.util.Collection;
import javafx.geometry.Point2D;

public class Level {
    public Collection<Entity> entities;
    public Entity player;
    public int gravity;
    
    public Level() { 
        entities = new ArrayList<>();
    }
    
    public static Level loadLevel(int levelIndex) {
        switch (levelIndex) {
            case 1:
                Level level = new Level();
                Entity player = new Entity("player", new Point2D(32, 32));
                level.player = player;
                level.entities.add(player);
                level.entities.add(new Entity("wall", new Point2D(96, 48)));
 
                for (int i = 0; i < 30; i++) {
                    level.entities.add(new Entity("wall", new Point2D(i * 16, 0)));
                    level.entities.add(new Entity("wall", new Point2D(i * 16, 464)));
                    level.entities.add(new Entity("wall", new Point2D(0, i * 16)));
                    level.entities.add(new Entity("wall", new Point2D(464, i * 16)));
                }
                
                return level;
            default:
                return new Level();
                
        } 
    }

    public void changeGravity(int i) {
        gravity += i;
    }

    public void gravitate() {
        // Go through gravitable entities and make them gravitate
        gravitation: for (Entity e : entities) {
            if (e.movable) {
                e.move(gravity);
                for (Entity collidable : entities) {
                    if (!collidable.equals(e)) {
                        if (e.collide(collidable)) {
                            e.move(gravity - 180);
                            continue gravitation;
                        }
                    }
                }
                
                
                
            }
        }
    }
}
