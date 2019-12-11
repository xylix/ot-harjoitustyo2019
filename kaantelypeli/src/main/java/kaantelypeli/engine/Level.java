package kaantelypeli.engine;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.Collection;
import javafx.scene.paint.Color;

/**
 * Handles level generation and level management. 
 * Exposes loadLevel static method for level generation and
 * gravitate() method for binding gravitation to game ticks.
 */
public class Level {
    public static final String VICTORY = "victory";
    public static final String PLAYER = "player";
    public static final String WALL = "wall";
    
    private final Collection<Entity> entities;
    int gravity;
    boolean won = false;
    
    private Level() { 
        entities = new ArrayList<>();
        gravity = 0;
    }
    
    /**
     * Method returns generated level number `index`.
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
            case 2:
                return two();
            default:
                return new Level();
        } 
    }
    
    private static Level negativeOne() {
        Level level = new Level();
        Entity playerZero = new Entity(PLAYER, 0, 16);
        level.entities.add(playerZero);
        level.entities.add(new Entity(VICTORY, 0, 24));

        return level;
    }
    
    private static Level zero() {
        Level level = new Level();
        Entity player = new Entity(PLAYER, 32, 32);
        level.entities.add(player);
        level.entities.add(new Entity(WALL, 96, 48));

        for (int i = 0; i < 15; i++) {
            level.entities.add(new Entity(WALL, i * 16, 0));
            level.entities.add(new Entity(WALL, i * 16, 224));
            level.entities.add(new Entity(WALL, 0, i * 16));
            level.entities.add(new Entity(WALL, 224, i * 16));
        }

        level.entities.add(new Entity(VICTORY, 96, 16));
        return level;
    }
    
    private static Level one() {
        Level level = new Level();
        Entity player = new Entity(PLAYER, 32, 32);
        level.entities.add(player);
        
        for (int i = 0; i < 15; i++) {
            level.entities.add(new Entity(WALL, i * 16, 0));
            level.entities.add(new Entity(WALL, i * 16, 224));
            level.entities.add(new Entity(WALL, 0, i * 16));
            level.entities.add(new Entity(WALL, 224, i * 16));
        }
        
        for (int i = 0; i < 15; i++) { 
            //avoid making a wall over the keyhole
            if (i != 5) {
                level.entities.add(new Entity(WALL, i * 16, 80));
            }
        }
        
        level.entities.add(new Entity("key", 48, 48));
        level.entities.add(new Entity("door", 80, 80));
        
        level.entities.add(new Entity(VICTORY, 48, 112));
        return level;
    }
    
    private static Level two() {
        Level level = new Level();
        Entity player = new Entity(PLAYER, 16, 32);
        level.entities.add(player);
        
        for (int i = 0; i < 15; i++) {
            level.entities.add(new Entity(WALL, i * 16, 0));
            level.entities.add(new Entity(WALL, i * 16, 224));
            level.entities.add(new Entity(WALL, 0, i * 16));
            level.entities.add(new Entity(WALL, 224, i * 16));
        }
        
        for (int i = 0; i < 15; i++) { 
            //avoid making a wall over the keyhole
            if (i != 5) {
                level.entities.add(new Entity(WALL, i * 16, 80));
            }
        }
        
        level.entities.add(new Entity("key", 16, 16));
        level.entities.add(new Entity("door", 80, 80));
        
        level.entities.add(new Entity(VICTORY, 48, 112));
        return level;
    }
    
    public Collection<Entity> getEntities() {
        return this.entities;
    }
    
    /**
     * Changes the gravity by `degrees`.
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
            if (won) {
                return;
            }
            collider.move(gravity);
            
            entities.stream().filter(collider::collide).forEach(collidee -> {
                String action = collider.collisionAction(collidee);
                switch (action) {
                    case VICTORY:
                        System.out.println("You're winner!");
                        won = true;
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

    /**
     * Converts the level's entity list to JSON, which can be used as the entire 
     * levels JSON representation.
     * @return JSON representation of the level
     */
    public String toJson() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Entity.class, new EntityAdapter());
        Gson gson = builder.create();
        return gson.toJson(entities);
    }
}
