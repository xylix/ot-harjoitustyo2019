package kaantelypeli.ui;

/**
 * 2nd Main class, helps maven-shade-plugin properly package JavaFX dependencies.
 */
public class Main {
    /**
     * Launches Game.main().
     * @param args
     */
    public static void main(String[] args) {
        kaantelypeli.ui.Game.main(args);
    }
}
