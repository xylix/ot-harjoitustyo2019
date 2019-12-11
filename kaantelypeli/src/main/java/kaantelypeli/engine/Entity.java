package kaantelypeli.engine;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import java.net.URL;
import java.util.Objects;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

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
    
    final String type;
    final int xCoord;
    final int yCoord;
    
    boolean movable = false;
    boolean passable = true;
    final transient Rectangle hitbox;
    
    /**
     * Creates a new entity of `type` at location `x`,`y`.
     * @param type Type of new entity. Defined values [WALL, PLAYER, 
     * VICTORY, KEY and DOOR].
     * @param x X-coordinate of new entity.
     * @param y Y-coordinate of new entity.
     */
    public Entity(String type, int x, int y) {
        this.type = type;
        this.hitbox = new Rectangle(x, y, 16, 16);
        xCoord = x;
        yCoord = y;
        hitbox.setId(type);
        // Fallback color if sprite not found
        hitbox.setFill(Color.GREEN);
        loadEntity(type);
    }
    
    
    private void loadEntity(String type) {
        switch (type) {
            case WALL:
                passable = false;
                break;
            case PLAYER:
                hitbox.setWidth(14);
                hitbox.setHeight(14);
                hitbox.setFill(Color.BLUE);
                movable = true;
                break;
            case VICTORY:
                break;
            case KEY:
                hitbox.setWidth(12);
                hitbox.setHeight(12);
                movable = true;
                passable = false;
                break;
            case DOOR:
                passable = false;
                break;
            case LAVA:
                break;
            default:
                System.out.println("Entity type not supported.");
                break;
        }
        loadSprite(type + ".png");
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
                hitbox.setTranslateY(hitbox.getTranslateY() + 1); 
                break;
            case 90:
                hitbox.setTranslateX(hitbox.getTranslateX() + 1);
                break;
            case 180:
                hitbox.setTranslateY(hitbox.getTranslateY() - 1);
                break;
            case 270:
                hitbox.setTranslateX(hitbox.getTranslateX() - 1);
                break;
            default:
                System.out.println("Illegal movement call");
                break;
        }
    }
    
    private void loadSprite(String filename) {
        URL spriteUrl = getClass().getClassLoader().getResource("sprites/" + filename);
        if (spriteUrl != null) {
            Image sprite = new Image(spriteUrl.toString());
            hitbox.setFill(new ImagePattern(sprite, 0, 0, 16, 16, false));
        } else {
            System.out.println("No sprite named: '" + filename + "' found");
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
        hash = 79 * hash + this.xCoord;
        hash = 79 * hash + this.yCoord;
        hash = 79 * hash + (this.movable ? 1 : 0);
        hash = 79 * hash + (this.passable ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Entity other = (Entity) obj;
        if (this.xCoord != other.xCoord) {
            return false;
        }
        if (this.yCoord != other.yCoord) {
            return false;
        }
        if (this.movable != other.movable) {
            return false;
        }
        if (this.passable != other.passable) {
            return false;
        }
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        return true;
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
     * Converts a JSON representation to an entity.
     * @param json A valid JSON representation of an entity.
     * @return Generated entity.
     */
    public static Entity fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Entity.class);
    }
}
