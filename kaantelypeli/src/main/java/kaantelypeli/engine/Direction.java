package kaantelypeli.engine;

import javafx.util.Pair;

public class Direction {
    public Pair<Float, Float> vector;
    Direction(int degrees) {
        double radians = Math.toRadians(degrees);
        this.vector = new Pair<Float, Float>((float)Math.cos(radians), (float)Math.sin(radians));
    }
}
