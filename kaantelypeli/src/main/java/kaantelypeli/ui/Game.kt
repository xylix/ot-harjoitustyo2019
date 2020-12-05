package kaantelypeli.ui

import javafx.animation.AnimationTimer
import javafx.application.Application
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.ChoiceDialog
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import javafx.stage.Stage
import kaantelypeli.engine.Level
import kaantelypeli.utils.loadLevel
import kaantelypeli.utils.createButton
import kaantelypeli.utils.selector
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * Main Graphical User Interface class.
 */
class Game : Application() {
    private var mainStage: Stage? = null
    private var mainMenu: Scene? = null
    override fun start(stage: Stage) {
        mainStage = stage
        val cloud: Button = createButton("cloud-levels") { event -> cloudMenu() }
        val editor = LevelEditor(stage, mainMenu)
        val editorButton: Button = createButton("editor") { event -> editor.editorMenu() }
        val buttons = VBox(cloud, editorButton)
        val levelFolder =
            InputStreamReader(Game::class.java.getResourceAsStream("/levels"))
        val br = BufferedReader(levelFolder)
        br.lines().forEach { line: String ->
            buttons.children.add(levelButton(line, stage))
        }
        mainMenu = Scene(buttons)
        stage.scene = mainMenu
        stage.show()
    }

    private fun cloudMenu() {
        val choice: ChoiceDialog<String> = selector(FILESERVER, mainStage)
        val result = choice.showAndWait()
        result.ifPresent { input: String ->
            mainStage!!.scene = toScene(
                loadLevel("$FILESERVER/$input")
            )
        }
    }

    private fun levelButton(file: String, stage: Stage): Button {
        val levelName = file.replace(".json", "")
        val button = createButton(levelName) { _ ->
            val activeLevel = loadLevel(levelName)
            stage.scene = toScene(activeLevel)
        }
        return button
    }

    private fun toScene(level: Level): Scene {
        val pane =
            Pane(*level.hitboxes.toTypedArray())
        val scene = Scene(pane)
        scene.onKeyPressed = EventHandler { event: KeyEvent ->
            val pressedKey = event.code
            if (pressedKey == KeyCode.LEFT || pressedKey == KeyCode.A) {
                level.changeGravity(270)
                pane.rotate = pane.rotate + 270
            } else if (pressedKey == KeyCode.RIGHT || pressedKey == KeyCode.D) {
                level.changeGravity(90)
                pane.rotate = pane.rotate + 90
            } else if (pressedKey == KeyCode.R) {
                level.restart()
            }
        }
        object : AnimationTimer() {
            override fun handle(timestamp: Long) {
                level.tick()
                level.ifOver(Runnable { mainStage!!.scene = mainMenu })
            }
        }.start()
        return scene
    }

    companion object {
        const val SCALE = 2
        const val FILESERVER = "http://xylix.fi/levels"

        /**
         * Launch the application.
         * @param args pass command line arguments to follow
         * main() method convention
         */
        @JvmStatic
        fun main(args: Array<String>) {
            launch()
        }
    }
}
