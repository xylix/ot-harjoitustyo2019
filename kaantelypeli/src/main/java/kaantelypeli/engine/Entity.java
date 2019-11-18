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
    public Entity(String type, Point2D location) {
        super(location.getX(), location.getY(), 16, 16);
        if (type.equals("player")) {
            super.setFill(Color.BLUE);
        } else if (type.equals("wall")) {
            super.setFill(Color.GRAY);
        }
        
    }
    
}
