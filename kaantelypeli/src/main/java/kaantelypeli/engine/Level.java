package kaantelypeli.engine;

import java.util.ArrayList;
import java.util.Collection;
import javafx.scene.paint.Color;

public class Level {
    private final Collection<Entity> entities;
    int gravity;
    boolean victory = false;
    
    public Level() { 
        entities = new ArrayList<>();
        gravity = 0;
    }
    
    public static Level loadLevel(int levelIndex) {
        switch (levelIndex) {
            case 0:
                return zero();
            case 1:
                return one();
            case 2:
                return two();
            default:
                return new Level();
                
        } 
    }
    
    private static Level zero() {
        Level level = new Level();
        Entity playerZero = new Entity("player", 0, 16);
        level.entities.add(playerZero);
        level.entities.add(new Entity("victory", 0, 48));

        return level;
    }
    
    private static Level one() {
        Level level = new Level();
        Entity player = new Entity("player", 32, 32);
        level.entities.add(player);
        level.entities.add(new Entity("wall", 96, 48));

        for (int i = 0; i < 15; i++) {
            level.entities.add(new Entity("wall", i * 16, 0));
            level.entities.add(new Entity("wall", i * 16, 224));
            level.entities.add(new Entity("wall", 0, i * 16));
            level.entities.add(new Entity("wall", 224, i * 16));
        }

        level.entities.add(new Entity("victory", 96, 16));
        return level;
    }
    
    private static Level two() {
        Level level = new Level();
        Entity player = new Entity("player", 32, 32);
        level.entities.add(player);
        
        for (int i = 0; i < 15; i++) {
            level.entities.add(new Entity("wall", i * 16, 0));
            level.entities.add(new Entity("wall", i * 16, 224));
            level.entities.add(new Entity("wall", 0, i * 16));
            level.entities.add(new Entity("wall", 224, i * 16));
        }
        
        for (int i = 0; i < 15; i++) { 
            //avoid making a wall over the keyhole
            if (i != 5) {
                level.entities.add(new Entity("wall", i * 16, 80));
            }
        }
        
        level.entities.add(new Entity("key", 48, 48));
        level.entities.add(new Entity("keyhole", 80, 80));
        
        level.entities.add(new Entity("victory", 48, 112));
        return level;
    }
    
    public Collection<Entity> getEntities() {
        return this.entities;
    }
    
    public void changeGravity(int degrees) {
        gravity += degrees;
    }

    public void gravitate() {
        entities.stream().filter(e -> (e.movable)).forEach(collider -> {
            if (victory) {
                return;
            }
            collider.move(gravity);
            
            entities.stream().filter(collider::collide).forEach(collidee -> {
                String action = collider.collisionAction(collidee);
                switch (action) {
                    case "victory":
                        System.out.println("You're winner!");
                        victory = true;
                        return;
                    case "open":
                        collidee.setFill(Color.PINK);
                        collidee.passable = true;
                        collider.setFill(Color.TRANSPARENT);
                        break;
                    case "blocked":
                        collider.move(gravity + 540);
                        break;
                    default:
                        break;
                }
            });
        });
    }
}
