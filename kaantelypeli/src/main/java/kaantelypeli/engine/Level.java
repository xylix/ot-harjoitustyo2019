package kaantelypeli.engine;

import java.util.ArrayList;
import java.util.Collection;
import javafx.application.Platform;
import javafx.geometry.Point2D;

public class Level {
    public Collection<Entity> entities;
    public Entity player;
    public int gravity;
    boolean freeze = false;
    
    public Level() { 
        entities = new ArrayList<>();
    }
    
    public static Level loadLevel(int levelIndex) {
        switch (levelIndex) {
            case 0:
                return zero();
            case 1:
                return one();
            default:
                return new Level();
                
        } 
    }
    
    private static Level zero() {
        Level level = new Level();
        Entity playerZero = new Entity("player", new Point2D(0, 32));
        level.entities.add(playerZero);
        level.entities.add(new Entity("victory", new Point2D(0, 48)));

        return level;
    }
    
    private static Level one() {
        Level level = new Level();
        Entity player = new Entity("player", new Point2D(32, 32));
        level.player = player;
        level.entities.add(player);
        level.entities.add(new Entity("wall", new Point2D(96, 48)));

        for (int i = 0; i < 15; i++) {
            level.entities.add(new Entity("wall", new Point2D(i * 16, 0)));
            level.entities.add(new Entity("wall", new Point2D(i * 16, 224)));
            level.entities.add(new Entity("wall", new Point2D(0, i * 16)));
            level.entities.add(new Entity("wall", new Point2D(224, i * 16)));
        }

        level.entities.add(new Entity("victory", new Point2D(96, 16)));
        return level;
    }
    
    

    public void changeGravity(int degrees) {
        gravity += degrees;
    }

    public void gravitate() {
        gravitation: for (Entity e : entities) {
            if (e.movable && !freeze) {
                e.move(gravity);
                for (Entity collidable : entities) {
                    if (e.type.equals("player") && collidable.type.equals("victory") && e.collide(collidable)) {
                            System.out.println("You're winner!");
                            freeze = true;
                            return;
                    } else if (!collidable.equals(e)) {
                        if (e.collide(collidable)) {
                            e.move(gravity + 540);
                            continue gravitation;
                        }
                    }
                }
            }
        }
    }
}
