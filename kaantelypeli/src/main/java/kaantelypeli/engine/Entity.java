package kaantelypeli.engine;

import javafx.scene.shape.Rectangle;
import javafx.geometry.Point2D;

/**
 *
 * @author xylix
 */
public class Entity extends Rectangle {   
    public Entity(Point2D location) {
        super(location.getX(), location.getY(), 16, 16);
    }
    
}
