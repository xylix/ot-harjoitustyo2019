package kaantelypeli.engine;
import com.google.gson.Gson;
import com.google.gson.annotations.JsonAdapter;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import kaantelypeli.utils.FileOperations;
import org.tinylog.Logger;

import java.util.HashMap;
import java.util.Objects;

import static kaantelypeli.ui.Game.SCALE;
import static kaantelypeli.utils.FileOperations.loadSprite;

/**
 * Exposes entity generation, movement and collision.
 */
@JsonAdapter(EntitySerializer.class)
public class Entity {
    final String type;
    final int x;
    final int y;
    
    private HashMap<String, String> actionMap;
    private Integer width;
    private Integer height;
    transient Rectangle hitbox;
    boolean movable;
    boolean passable;
    private Direction velocity;

    /**
     Creates a new entity of `type` at location `x`,`y` with default width and height.
     * @param type Type of new entity. See resources/entities for info.
     * @param x X-coordinate of new entity.
     * @param y Y-coordinate of new entity.
     */
    public Entity(String type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;
        setProperties();
    }
    
    /**
     * Creates a new entity of `type` at location `x`,`y` with 
     * non-default width and height.
     * @param type Type of new entity. See resources/entities for info.
     * @param x X-coordinate of new entity.
     * @param y Y-coordinate of new entity.
     * @param width width of new entity.
     * @param height height of new entity.
     */
    public Entity(String type, int x, int y, int width, int height) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        setProperties();
    }

    /*
     * Sets entity properties based on entity types definition file.
     * Definition files located in entities/
     */
    final void setProperties() {
        Properties source = FileOperations.loadProperties(type);
        if (width == null || height == null) {
            width = source.width;
            height = source.height;
        }
        movable = source.movable;
        passable = source.passable;
        actionMap = Objects.requireNonNullElseGet(source.actionMap, HashMap::new);

        this.hitbox = new Rectangle(x * SCALE, y * SCALE, width * SCALE, height * SCALE);
        hitbox.setId(type);
        this.hitbox.setFill(loadSprite(Objects.requireNonNullElseGet(source.graphics, () -> type + ".png")));

        this.velocity = new Direction(0, 0);
    }

    /**
     * Collision check.
     * @param collided Entity to check collision with.
     * @return True if this entity is currently colliding with collided.
     */
    public boolean willCollide(Entity collided) {
        // Virtual width / height of the box that defines _will_ the collision happen if this entity is moved
        // by its velocity vector

        Shape futureBox = new Polygon(
                getActualX() + width + velocity.getX(),
                getActualX() + velocity.getX(),
                getActualY() + height + velocity.getY(),
                getActualY() + velocity.getY()
        );
        Shape collisionBox = Shape.intersect(futureBox, collided.hitbox);
        return collisionBox.getBoundsInLocal().getWidth() != -1;
    }
    
    /**
     * Deducts what happens in a collision.
     * @param collided Entity to collide with.
     * @return String defining what to do when `this` and `collided` collide.
     */
    public String collisionAction(Entity collided) {
        return this.actionMap.getOrDefault(collided.type, "");
    }

    void move() {
        hitbox.setTranslateX(hitbox.getTranslateX() + velocity.getX() * SCALE);
        hitbox.setTranslateY(hitbox.getTranslateY() + velocity.getY() * SCALE);
    }

    private String lastLogged = null;
    void gravitate(Direction gravity) {
        this.velocity.add(gravity);
        if (!gravity.toString().equals(lastLogged)) Logger.trace(gravity);
        lastLogged = gravity.toString();
    }

    public double getActualX() {
        return hitbox.getX() + hitbox.getTranslateX();
    }
    
    public double getActualY() {
        return hitbox.getY() + hitbox.getTranslateY();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.type);
        hash = 79 * hash + this.x;
        hash = 79 * hash + this.y;
        hash = 79 * hash + (this.movable ? 1 : 0);
        hash = 79 * hash + (this.passable ? 1 : 0);
        hash = 79 * hash + this.actionMap.hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (this == obj) {
            return true;
        } else if (getClass() != obj.getClass()) {
            return false;
        }
        
        final Entity other = (Entity) obj;
        return this.getJson().equals(other.getJson());
    }

    /**
     * Converts this entity into a JSON representation.
     * @return JSON representation of the entity.
     */
    public String getJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public Rectangle getHitbox() {
        return this.hitbox;
    }
}
