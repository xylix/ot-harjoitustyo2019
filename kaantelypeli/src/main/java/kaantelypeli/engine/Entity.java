package kaantelypeli.engine;

import javafx.scene.shape.Rectangle;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public class Entity extends Rectangle {
    boolean movable;
    boolean passable;
    String type;
    
    public Entity(String type, Point2D location) {
        super(location.getX(), location.getY(), 16, 16);
        this.type = type;
        movable = false;
        passable = true;
        
        switch (type) {
            case "wall":
                super.setFill(Color.GRAY);
                passable = false;
                break;
            case "player":
                super.setFill(Color.BLUE);
                movable = true;
                break;
            case "victory":
                super.setFill(Color.WHITE);
                break;
            case "keyCrate":
                super.setFill(Color.BROWN);
                movable = true;
                passable = false;
                break;
            case "keyhole":
                super.setFill(Color.RED);
                break;
            default:
                break;
        }
    }
    
    public boolean collide(Entity collidee) {
        Shape collisionBox = Shape.intersect(this, collidee);
        return collisionBox.getBoundsInLocal().getWidth() != -1;
    }
    
    public String collisionAction(Entity collidee) {
        if (this.type.equals("player") && collidee.type.equals("victory")) {
            return "victory";
        } else if (this.type.equals("keyCrate") && collidee.type.equals("keyhole")) {
            return "open";
        } else if (!this.equals(collidee)) {
            if (!passable) {
                return "blocked";
            }
        }
        return "";
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
                
        }
    }
    
}
