package kaantelypeli.engine;
import static kaantelypeli.fs.FileOperations.loadSprite;

import com.google.gson.Gson;
import java.util.Objects;

import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 * Exposes entity generation, movement and collision.
 */
public class Entity {

    public static final String VICTORY = "victory";
    public static final String PLAYER = "player";
    public static final String KEY = "key";
    public static final String DOOR = "door";
    public static final String WALL = "wall";
    public static final String LAVA = "lava";
    public static final int SCALE = 2;
    
    final String type;
    final int x;
    final int y;
    final int width;
    final int height;
    
    boolean movable;
    boolean passable;
    private transient Rectangle hitbox;
    
    /**
     * Creates a new entity of `type` at location `x`,`y`.
     * @param type Type of new entity. Defined values [WALL, PLAYER, 
     * VICTORY, KEY and DOOR].
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
        this.movable = false;
        this.passable = true;
        setProperties(type);
    }
    
    private void setProperties(String type) {
        switch (type) {
            case WALL:
            case DOOR:
                passable = false;
                break;
            case PLAYER:
                movable = true;
                break;
            case KEY:
                movable = true;
                passable = false;
                break;
            case VICTORY:
            case LAVA:
                break;
            default:
                System.out.println("Entity type not supported.");
                break;
        }
    }

    /**
     * Collision check.
     * @param collidee Entity to check collision with.
     * @return returns True if `this` entity is currently colliding with `collidee`
     */
    public boolean collide(Entity collidee) {
        Shape collisionBox = Shape.intersect(hitbox, collidee.hitbox);
        return collisionBox.getBoundsInLocal().getWidth() != -1;
    }
    
    /**
     * Deducts what happens in a collision.
     * @param collidee Entity to collide with.
     * @return String defining what to do when `this` and `collidee` collide.
     */
    public String collisionAction(Entity collidee) {
        if (this.type.equals(PLAYER) && collidee.type.equals(VICTORY)) {
            return VICTORY;
        } else if (this.type.equals(PLAYER) && collidee.type.equals(LAVA)) {
            return "loss";
        } else if (this.type.equals(KEY) && collidee.type.equals(DOOR)) {
            return "open";
        } else if (!this.equals(collidee) && !collidee.passable) {
            return "blocked";
        } else {
            return "";
        }
    }

    void move(int i) {
        i = Math.abs(i % 360);
        switch (i) {
            case 0:
                hitbox.setTranslateY(hitbox.getTranslateY() + 1 * SCALE); 
                break;
            case 90:
                hitbox.setTranslateX(hitbox.getTranslateX() + 1 * SCALE);
                break;
            case 180:
                hitbox.setTranslateY(hitbox.getTranslateY() - 1 * SCALE);
                break;
            case 270:
                hitbox.setTranslateX(hitbox.getTranslateX() - 1 * SCALE);
                break;
            default:
                System.out.println("Illegal movement call");
                break;
        }
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
        return this.toJson().equals(other.toJson());
    }

    /**
     * Converts this entity into a JSON representation.
     * @return JSON representation of the entity.
     */
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    /**
     * Returns the entity hitbox or sets and returns if does not exist.
     * @return Rectangle representing the Entity's JavaFX existence
     */
    public Rectangle getHitbox() {
        if (this.hitbox == null) {
            this.hitbox = new Rectangle(x * SCALE, y * SCALE, width * SCALE, height * SCALE);
            hitbox.setId(type);
            hitbox.setFill(loadSprite(type, SCALE));
        }
        return hitbox;
    }
}
