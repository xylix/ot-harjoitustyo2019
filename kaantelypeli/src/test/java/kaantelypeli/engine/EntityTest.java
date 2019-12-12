package kaantelypeli.engine;

import java.util.concurrent.TimeoutException;
import javafx.stage.Stage;
import kaantelypeli.ui.Game;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.testfx.api.FxToolkit;

public class EntityTest {
    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();
    private Stage stage;
    
    @Before
    public void setUp() throws TimeoutException {
        stage = FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(Game.class);
    }
    
    @Test
    public void illegalMovementTest() {
        Entity test = new Entity("player", 0, 0);
        double x = test.getHitbox().getX() + test.getHitbox().getTranslateX();
        double y = test.getHitbox().getY() + test.getHitbox().getTranslateY();
        test.move(4);
        assertEquals(x, test.getActualX(), 0);
        assertEquals(y, test.getActualY(), 0);
        assertEquals("Illegal movement call" + System.lineSeparator(), systemOutRule.getLog());
    }
    
    @Test
    public void spriteNotFoundTest() {
        Entity test = new Entity("test", 0, 0);
        assertEquals("Entity type not supported." + System.lineSeparator(), systemOutRule.getLog());
    }
    
    @Test
    public void toJsonTest() {
        Entity e = new Entity("player", 0, 0);
        assertEquals("{\"type\":\"player\",\"xCoord\":0,\"yCoord\":0,\"movable\":true,\"passable\":true}", e.toJson());
    }
}
