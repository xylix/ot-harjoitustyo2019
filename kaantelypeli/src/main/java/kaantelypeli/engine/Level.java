package kaantelypeli.engine;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.tinylog.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Handles level generation and level management. 
 * Exposes loadLevel static method for level generation and
 * gravitate() method for binding gravitation to game ticks.
 */
public class Level {
    Direction gravity;
    boolean won;
    boolean lost;
    private List<Entity> entities;
    
    private Level() {
        entities = new ArrayList<>();
        gravity = new Direction(0, 0.1);
        won = false;
        lost = false;
    }

    /**
     * Creates level from entity list.
     * LevelEditor uses this.
     * @param entities list of entities to place on level
     */
    public Level(List<Entity> entities) {
        this();
        this.entities = entities;
    }

    /**
     * When level has been won, perform action.
     * @param action Action to call when level has been won
     */
    public void ifOver(Runnable action) {
        if (won) {
            action.run();
        }
    }

    /**
     * Changes the gravity by `degrees`.
     *
     * @param   degrees   Wanted change in degrees
     */
    public void changeGravity(int degrees) {
        gravity.rotate(degrees);
    }

    /**
     * Method to externally bind this level to game ticks. Should get called every tick when level is active.
     */
    public void tick() {
        if (won) {
            return;
        } else if (lost) {
            entities.forEach(e -> {
                e.hitbox.setTranslateX(0);
                e.hitbox.setTranslateY(0);
            });
            lost = false;
            return;
        }
        gravitate();
    }

    /** Applies gravity to all entities on the level. Movables get moved here. */
    private void gravitate() {
        entities.stream().filter(e -> (e.movable)).forEach(collider -> {
            collider.gravitate(gravity);
            for (Entity collided : entities)
            if (collider.willCollide(collided)) {
                String action = collider.collisionAction(collided);
                handleAction(collider, collided, action);
            } else {
                collider.move();
            }
        });
    }
    /** Resolves Entity interaction. */
    private void handleAction(Entity collider, Entity collidee, String action) {
        if (!action.isBlank()) {
            Logger.trace(action);
        }
        if ("victory".equals(action)) {
            System.out.println("You're winner!");
            won = true;
        } else if ("loss".equals(action)) {
            lost = true;
        } else if ("open".equals(action)) {
            collidee.passable = true;
            collidee.hitbox.setFill(Color.TRANSPARENT);
            collider.movable = false;
            collider.hitbox.setFill(Color.TRANSPARENT);
        } else if (!collidee.passable) {
            collider.undoMove();
        }
    }

    /**
     * Converts the level to JSON.
     * @return JSON representation of the level
     */
    public String getJson() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }

    public List<Rectangle> getHitboxes() {
        return entities.stream().map(e -> e.hitbox).collect(Collectors.toList());
    }

    public List<Entity> getEntities() {
        return Collections.unmodifiableList(entities);
    }
}
