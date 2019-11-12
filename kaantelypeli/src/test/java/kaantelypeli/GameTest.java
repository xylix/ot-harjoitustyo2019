package kaantelypeli;

import java.util.concurrent.TimeoutException;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

public class GameTest extends ApplicationTest {
    
    @Test
    public void launchTest()  {
        
    }
    
    
    @Before
    public void setUp() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(Game.class);
    }
    
}
