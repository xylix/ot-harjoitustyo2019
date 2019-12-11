package kaantelypeli.engine;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Handles level generation and level management. 
 * Exposes loadLevel static method for level generation and
 * gravitate() method for binding gravitation to game ticks.
 */
public class Level {
    public static final String VICTORY = "victory";
    public static final String PLAYER = "player";
    public static final String WALL = "wall";
    public static final String LAVA = "lava";
    
    private final ArrayList<Entity> entities;
    int gravity;
    int id;
    boolean won = false;
    boolean lost = false;
    
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
        level.id = -1;
        Entity playerZero = new Entity(PLAYER, 0, 16);
        level.entities.add(playerZero);
        level.entities.add(new Entity(VICTORY, 0, 24));

        return level;
    }
    
    private static Level zero() {
        Level level = new Level();
        level.id = 0;
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
        level.id = 1;
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
        level.id = 2;
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
                level.entities.add(new Entity(LAVA, i * 16, 80));
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
        if (won) {
            return;
        } else if (lost) {
            // Does not reset keys / doors
            entities.stream().forEach((e) -> {
                e.hitbox.setTranslateX(0);
                e.hitbox.setTranslateY(0);
            });
            lost = false;
            return;
        }
        
        entities.stream().filter(e -> (e.movable)).forEach(collider -> {
            collider.move(gravity);
            entities.stream().filter(collider::collide).forEach(collidee -> {
                String action = collider.collisionAction(collidee);
                switch (action) {
                    case VICTORY:
                        System.out.println("You're winner!");
                        won = true;
                        return;
                    case "loss":
                        //System.out.println("You have lost this level " + losses + "times");
                        lost = true;
                        break;
                    case "open":
                        collidee.hitbox.setFill(Color.TRANSPARENT);
                        collidee.passable = true;
                        collider.hitbox.setFill(Color.TRANSPARENT);
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
        Gson gson = new Gson();
        return gson.toJson(entities);
    }

    public List<Rectangle> getHitboxes() {
        return entities.stream().map(e -> e.hitbox).collect(Collectors.toList());
    }
}
