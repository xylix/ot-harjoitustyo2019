package kaantelypeli.engine;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
    
    @Test
    public void jsonTest() {
        Entity e = new Entity("player", 0, 0);
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Entity.class, new EntityAdapter());
        Gson gson = builder.create();
        String json = gson.toJson(e);
        assertEquals("\"{player,0.0,0.0}\"", e.toJson());
    }
}
