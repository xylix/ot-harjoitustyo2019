package kaantelypeli.ui

/**
 * 2nd Main class, helps maven-shade-plugin properly package JavaFX dependencies.
 */
object Main {
    /**
     * Launches Game.main().
     * @param args pass command line arguments to follow
     * main() method convention
     */
    @JvmStatic
    fun main(args: Array<String>) {
        val game = kaantelypeli.ui.Game()
        game.main(args)
    }
}
