package kaantelypeli.engine;

import javafx.scene.shape.Rectangle;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

/**
 *
 * @author xylix
 */
public class Entity extends Rectangle {
    public boolean movable;
    
    public Entity(String type, Point2D location) {
        super(location.getX(), location.getY(), 16, 16);
        if (type.equals("player")) {
            super.setFill(Color.BLUE);
            movable = true;
        } else if (type.equals("wall")) {
            super.setFill(Color.GRAY);
            movable = false;
        }
        
    }

    void move(int i) {
        i = i % 360;
        switch (i) {
            case 0:
                this.setTranslateY(this.getTranslateY() - 16);
            case 90:
                this.setTranslateX(this.getTranslateX() - 16);
            case 180:
                this.setTranslateY(this.getTranslateY() + 16);
            case 270:
                this.setTranslateX(this.getTranslateY() + 16);
        }
    }
    
}
