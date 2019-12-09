package kaantelypeli.engine;

import java.util.ArrayList;
import java.util.Collection;
import javafx.scene.paint.Color;

/**
 * Handles level generation and level management. 
 * Exposes loadLevel static method for level generation and
 * gravitate() method for binding gravitation to game ticks 
 */
public class Level {
    private final Collection<Entity> entities;
    int gravity;
    boolean victory = false;
    
    private Level() { 
        entities = new ArrayList<>();
        gravity = 0;
    }
    
    /**
     * Method returns generated level from index
     *
     * @param   index   index of wanted level
     * 
     * @return generated Level
     */
    
    public static Level loadLevel(int index) {
        switch (index) {
            case -1:
                return negativeOne();
            case 0:
                return zero();
            case 1:
                return one();
            default:
                return new Level();
        } 
    }
    
    private static Level negativeOne() {
        Level level = new Level();
        Entity playerZero = new Entity("player", 0, 16);
        level.entities.add(playerZero);
        level.entities.add(new Entity("victory", 0, 48));

        return level;
    }
    
    private static Level zero() {
        Level level = new Level();
        Entity player = new Entity("player", 32, 32);
        level.entities.add(player);
        level.entities.add(new Entity("wall", 96, 48));

        for (int i = 0; i < 15; i++) {
            level.entities.add(new Entity("wall", i * 16, 0));
            level.entities.add(new Entity("wall", i * 16, 224));
            level.entities.add(new Entity("wall", 0, i * 16));
            level.entities.add(new Entity("wall", 224, i * 16));
        }

        level.entities.add(new Entity("victory", 96, 16));
        return level;
    }
    
    private static Level one() {
        Level level = new Level();
        Entity player = new Entity("player", 32, 32);
        level.entities.add(player);
        
        for (int i = 0; i < 15; i++) {
            level.entities.add(new Entity("wall", i * 16, 0));
            level.entities.add(new Entity("wall", i * 16, 224));
            level.entities.add(new Entity("wall", 0, i * 16));
            level.entities.add(new Entity("wall", 224, i * 16));
        }
        
        for (int i = 0; i < 15; i++) { 
            //avoid making a wall over the keyhole
            if (i != 5) {
                level.entities.add(new Entity("wall", i * 16, 80));
            }
        }
        
        level.entities.add(new Entity("key", 48, 48));
        level.entities.add(new Entity("door", 80, 80));
        
        level.entities.add(new Entity("victory", 48, 112));
        return level;
    }
    
    public Collection<Entity> getEntities() {
        return this.entities;
    }
    
    /**
     * Changes the gravity by `degrees`
     *
     * @param   degrees   Wanted change in degrees
     */

    public void changeGravity(int degrees) {
        gravity += degrees;
    }

    /**
     * Applies gravity to all entities on the level.
     * Movable get moved here.
     */
    public void gravitate() {
        entities.stream().filter(e -> (e.movable)).forEach(collider -> {
            if (victory) {
                return;
            }
            collider.move(gravity);
            
            entities.stream().filter(collider::collide).forEach(collidee -> {
                String action = collider.collisionAction(collidee);
                switch (action) {
                    case "victory":
                        System.out.println("You're winner!");
                        victory = true;
                        return;
                    case "open":
                        collidee.setFill(Color.TRANSPARENT);
                        collidee.passable = true;
                        collider.setFill(Color.TRANSPARENT);
                        break;
                    case "blocked":
                        collider.move(gravity + 540);
                        break;
                    default:
                        break;
                }
            });
        });
    }
}
