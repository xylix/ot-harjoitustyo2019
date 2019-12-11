package kaantelypeli.engine;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.net.URL;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

/**
 * Exposes entity generation, movement and collision.
 */
public class Entity extends Rectangle {
    public static final String VICTORY = "victory";
    public static final String PLAYER = "player";
    public static final String KEY = "key";
    public static final String DOOR = "door";
    public static final String WALL = "wall";
    
    boolean movable;
    boolean passable;
    String type;
    
    /**
     * Creates a new entity of `type` at location `x`,`y`.
     * @param type Type of new entity. Defined values [WALL, PLAYER, 
     * VICTORY, KEY and DOOR].
     * @param x X-coordinate of new entity.
     * @param y Y-coordinate of new entity.
     */
    public Entity(String type, int x, int y) {
        super(x, y, 16, 16);
        this.type = type;
        movable = false;
        passable = true;
        super.setId(type);
        // Fallback color if sprite not found
        super.setFill(Color.GREEN);
        switch (type) {
            case WALL:
                passable = false;
                break;
            case PLAYER:
                this.setWidth(14);
                this.setHeight(14);
                super.setFill(Color.BLUE);
                movable = true;
                break;
            case VICTORY:
                break;
            case KEY:
                this.setWidth(12);
                this.setHeight(12);
                movable = true;
                passable = false;
                break;
            case DOOR:
                passable = false;
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
        Shape collisionBox = Shape.intersect(this, collidee);
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
                this.setTranslateY(this.getTranslateY() + 1); 
                break;
            case 90:
                this.setTranslateX(this.getTranslateX() + 1);
                break;
            case 180:
                this.setTranslateY(this.getTranslateY() - 1);
                break;
            case 270:
                this.setTranslateX(this.getTranslateX() - 1);
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
            this.setFill(new ImagePattern(sprite, 0, 0, 16, 16, false));
        } else {
            System.out.println("No sprite named: '" + filename + "' found");
        }
    }

    public double getActualX() {
        return this.getX() + this.getTranslateX();
    }
    
    public double getActualY() {
        return this.getY() + this.getTranslateY();
    }
    
    /**
     * Converts the entity into a JSON representation 
     * @return JSON representation of the entity.
     */
    public String toJson() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Entity.class, new EntityAdapter());
        Gson gson = builder.create();
        return gson.toJson(this);
    }
}
