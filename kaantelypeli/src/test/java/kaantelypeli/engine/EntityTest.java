package kaantelypeli.engine;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.contrib.java.lang.system.SystemOutRule;

/**
 *
 * @author xylix
 */
public class EntityTest {
    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();
    
    @Test
    public void illegalMovementTest() {
        Entity test = new Entity("player", 0, 0);
        double x = test.getX() + test.getTranslateX();
        double y = test.getY() + test.getTranslateY();
        test.move(4);
        assertEquals(x, test.getActualX(), 0);
        assertEquals(y, test.getActualY(), 0);
        assertEquals("Illegal movement call" + System.lineSeparator(), systemOutRule.getLog());
    }
    
    @Test
    public void spriteNotFoundTest() {
        Entity test = new Entity("test", 0, 0);
        assertEquals("Entity type not supported." + System.lineSeparator() + 
            "No sprite named: 'test.png' found" + System.lineSeparator(), systemOutRule.getLog());
    }
}
