package kaantelypeli.engine;

import javafx.scene.shape.Rectangle;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public class Entity extends Rectangle {
    boolean movable;
    String type;
    
    public Entity(String type, Point2D location) {
        super(location.getX(), location.getY(), 16, 16);
        this.type = type;
        switch (type) {
            case "wall":
                super.setFill(Color.GRAY);
                movable = false;
                break;
            case "player":
                super.setFill(Color.BLUE);
                movable = true;
                break;
            case "victory":
                super.setFill(Color.WHITE);
                movable = false;
                break;
            default:
                break;
        }
    }
    
    public boolean collide(Entity entity) {
        Shape collisionBox = Shape.intersect(this, entity);
        return collisionBox.getBoundsInLocal().getWidth() != -1;
    }

    void move(int i) {
        i = Math.abs(i % 360);
        switch (i) {
            case 0:
                this.setTranslateY(this.getTranslateY() - 1); 
                break;
            case 90:
                this.setTranslateX(this.getTranslateX() + 1);
                break;
            case 180:
                this.setTranslateY(this.getTranslateY() + 1);
                break;
            case 270:
                this.setTranslateX(this.getTranslateX() - 1);
                break;
            default:
                
        }
    }
    
}
