package kaantelypeli.engine;

import java.util.concurrent.TimeoutException;

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
    
    @Before
    public void setUp() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(Game.class);
    }
    
    @Test
    public void illegalMovementTest() {
        Entity test = new Entity("player", 0, 0, 16, 16);
        double x = test.getHitbox().getX() + test.getHitbox().getTranslateX();
        double y = test.getHitbox().getY() + test.getHitbox().getTranslateY();
        test.move(4);
        assertEquals(x, test.getActualX(), 0);
        assertEquals(y, test.getActualY(), 0);
        assertEquals("Illegal movement call" + System.lineSeparator(), systemOutRule.getLog());
    }
    
    @Test
    public void spriteNotFoundTest() {
        new Entity("test", 0, 0, 16, 16);
        assertEquals("Entity type not supported." + System.lineSeparator(), systemOutRule.getLog());
    }
    
    @Test
    public void toJsonTest() {
        Entity e = new Entity("player", 0, 0, 16, 16);
        assertEquals("{\"type\":\"player\",\"x\":0,\"y\":0,\"width\":16,\"height\":16,\"movable\":true,\"passable\":true}", e.toJson());
    }
    
    @Test
    public void hashCodeTest() {
        Entity e = new Entity("player", 0, 0, 16, 16);
        Entity e2 = new Entity("player", 0, 0, 16, 16);
        assertEquals(e.hashCode(), e2.hashCode());
        Entity e3 = new Entity("wall", 0, 0, 16, 16);
        assertNotEquals(e.hashCode(), e3.hashCode());
    }
    
    @Test
    public void getHitboxTest() {
        Entity e = new Entity("player", 0, 0, 16, 16);
        assertNotNull(e.getHitbox());
    }
}
