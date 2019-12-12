package kaantelypeli.engine;

import com.google.gson.Gson;
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
    boolean won;
    boolean lost;
    
    private Level() { 
        entities = new ArrayList<>();
        gravity = 0;
        won = false;
        lost = false;
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
            entities.stream().forEach(e -> {
                e.getHitbox().setTranslateX(0);
                e.getHitbox().setTranslateY(0);
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
                        lost = true;
                        break;
                    case "open":
                        collidee.getHitbox().setFill(Color.TRANSPARENT);
                        collidee.passable = true;
                        collider.getHitbox().setFill(Color.TRANSPARENT);
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
        return gson.toJson(this);
    }

    public List<Rectangle> getHitboxes() {
        return entities.stream().map(Entity::getHitbox).collect(Collectors.toList());
    }
}
