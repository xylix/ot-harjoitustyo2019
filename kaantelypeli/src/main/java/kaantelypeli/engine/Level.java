package kaantelypeli.engine;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.tinylog.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles level generation and level management. 
 * Exposes gravitate() method for binding gravitation to game ticks.
 */
public class Level {
    int gravity;
    boolean won;
    boolean lost;
    public int levelIndex = -1;
    private List<Entity> entities;

    
    private Level() { 
        entities = new ArrayList<>();
        gravity = 0;
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
     * Changes the gravity by `degrees`.
     *
     * @param   degrees   Wanted change in degrees
     */
    public void changeGravity(int degrees) {
        gravity += degrees;
    }

    public enum State {
        WON,
        LOST,
        ONGOING;
    }
    /**
     * Method to externally bind this level to game ticks. Should get called every tick when level is active.
     */
    public State tick() {
        if (won) {
            return State.WON;
        } else if (lost) {
            this.restart();
            return State.LOST;
        } else {
            gravitate();
            return State.ONGOING;
        }
    }

    public void restart() {
        entities.forEach(e -> {
            e.hitbox.setTranslateX(0);
            e.hitbox.setTranslateY(0);
        });
        lost = false;
    }

    /** Applies gravity to all entities on the level. Movables get moved here. */
    private void gravitate() {
        entities.stream().filter(e -> (e.movable)).forEach(collider -> {
            collider.move(gravity);
            entities.stream().filter(collider::collide).forEach(collidee -> {
                String action = collider.collisionAction(collidee);
                handleAction(collider, collidee, action);
            });
        });
    }

    enum Action {
        loss,
        open,
        victory,
        blank;
    }
    /** Resolves Entity interaction. */
    private void handleAction(Entity collider, Entity collidee, String rawAction) {
        Action action = Action.valueOf(rawAction);
        if (!collidee.passable) {
            collider.move(gravity + 540);
        }
        if (action == Action.blank) {
            return;
        } else if (action == Action.victory) {
            System.out.println("You're winner!");
            won = true;
        } else if (action == Action.loss) {
            lost = true;
        } else if (action == Action.open) {
            collidee.passable = true;
            collidee.hitbox.setFill(Color.TRANSPARENT);
            collider.movable = false;
            collider.hitbox.setFill(Color.TRANSPARENT);
        } 
        Logger.trace(rawAction);
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
