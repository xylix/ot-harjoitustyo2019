package kaantelypeli.engine;

import com.google.gson.Gson;
import java.net.URL;
import java.util.Objects;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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
    final int xCoord, yCoord, width, height;
    
    boolean movable = false;
    boolean passable = true;
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
        xCoord = x;
        yCoord = y;
        this.width = width;
        this.height = height;
        setProps(type);
    }
    
    private void setProps(String type) {
        switch (type) {
            case WALL:
                passable = false;
                break;
            case PLAYER:
                movable = true;
                break;
            case VICTORY:
                break;
            case KEY:
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
        URL spriteUrl = getClass().getClassLoader().getResource("sprites/" + filename + ".png");
        if (spriteUrl != null) {
            Image sprite = new Image(spriteUrl.toString());
            getHitbox().setFill(new ImagePattern(sprite, 0, 0, 16, 16, false));
        } else {
            System.out.println("No sprite named: '" + filename + "' found");
            getHitbox().setFill(Color.GREEN);
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
        if (this.hitbox != null) {
            return hitbox;
        } else {
            this.hitbox = new Rectangle(xCoord, yCoord, width, height);
            hitbox.setId(type);
            loadSprite(type);
            return hitbox;
        }
    }
}
