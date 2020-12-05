package kaantelypeli.ui

import javafx.collections.FXCollections
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.ChoiceBox
import javafx.scene.control.ChoiceDialog
import javafx.scene.control.TextField
import javafx.scene.control.TextInputDialog
import javafx.scene.input.MouseEvent
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import javafx.scene.shape.Rectangle
import javafx.stage.FileChooser
import javafx.stage.Stage
import kaantelypeli.engine.Entity
import kaantelypeli.engine.Level
import kaantelypeli.ui.FILESERVER
import kaantelypeli.ui.SCALE
import kaantelypeli.utils.Parsing.Companion.valueOfWithDefault
import kaantelypeli.utils.createButton
import kaantelypeli.utils.getClassResource
import kaantelypeli.utils.loadLevel
import kaantelypeli.utils.selector
import kaantelypeli.utils.uploadLevel
import org.tinylog.kotlin.Logger
import java.io.FileWriter
import java.io.IOException
import java.util.*

class LevelEditor
/**
 * Create a new LevelEditor object.
 * @param stage Parent stage for the opened editor
 * @param mainMenu main menu scene, needed here to enable returning.
 */(private val stage: Stage, private val mainMenu: Scene?) {
    private var editing: Level? = null
    private var levelName: String? = null

    /** Entry point, starts a selection dialog.  */
    fun editorMenu() {
        val choice: ChoiceDialog<String> = selector(FILESERVER, stage)
        val result = choice.showAndWait()
        result.ifPresent { input: String ->
            levelName = input
            stage.scene = editor(input)
        }
    }

    private fun editor(level: String): Scene {
        val pane = Pane()
        pane.setPrefSize((240 * SCALE).toDouble(), (240 * SCALE).toDouble())
        editing = loadLevel(level)
        pane.children.addAll(editing!!.hitboxes)
        val entities =
            ArrayList(editing!!.getEntities())
        val vbox = VBox(pane)
        vbox.prefHeight = ((240 + 16) * SCALE).toDouble()
        val menu: Button = createButton("menu") { event -> stage.scene = mainMenu }
        val buttons = HBox(menu)
        buttons.children.add(saveButton())
        buttons.children.add(uploadButton())
        vbox.children.add(buttons)
        pane.onMouseClicked = EventHandler { click: MouseEvent ->
            val x = click.sceneX.toInt()
            val y = click.sceneY.toInt()
            val node = tileEditDialog(
                floorToScale(x),
                floorToScale(y),
                stage
            )
            if (node != null) {
                pane.children.add(node)
                val e = Entity(
                    node.id,
                    node.x.toInt() / SCALE, node.y.toInt() / SCALE,
                    node.width.toInt() / SCALE, node.height.toInt() / SCALE
                )
                entities.add(e)
                Logger.trace(e.json)
            }

            editing = Level(entities)
        }
        Logger.trace("Opened a copy of level $level in editor")
        return Scene(vbox)
    }

    private fun saveButton(): Button {
        return createButton("save") { c ->
            val chooser = FileChooser()
            chooser.initialFileName = "edited"
            chooser.initialDirectory = getClassResource("levels", LevelEditor::class.java)
            chooser.selectedExtensionFilter = FileChooser.ExtensionFilter("JSON files", "*.json")
            chooser.initialFileName = levelName
            val saveLocation = chooser.showSaveDialog(stage)
            // Null if user cancels dialog or OS dialog doesn't work.
            if (saveLocation != null) {
                try {
                    FileWriter(saveLocation).use { fw ->
                        fw.write(editing!!.json)
                        Logger.trace("Saving file to " + saveLocation.path)
                    }
                } catch (e: IOException) {
                    Logger.error(e)
                }
            } else {
                Logger.trace("Save dialog cancelled")
            }
        }
    }

    private fun uploadButton(): Button {
        return createButton("upload") { c ->
            val url: String = FILESERVER.toString() + "/levels/" + levelName
            Logger.trace("POSTing to $url")
            Logger.trace(uploadLevel(url, editing!!))
        }
    }

    companion object {
        private fun floorToScale(i: Int): Int {
            return i / (16 * SCALE) * (16 * SCALE) / SCALE
        }

        private fun tileEditDialog(
            x: Int,
            y: Int,
            stage: Stage
        ): Rectangle? {
            val type = ChoiceBox<String>()
            type.setItems(
                FXCollections.observableArrayList(
                    listOf(
                        "wall",
                        "victory",
                        "player",
                        "lava",
                        "key",
                        "door"
                    )
                )
            )
            val width = TextField()
            width.promptText = "width"
            val height = TextField()
            height.promptText = "height"
            val inputs = VBox(type, width, height)
            inputs.id = "editInputs"
            val dialog = TextInputDialog()
            dialog.headerText = "Adding a tile at [$x,$y]"
            dialog.title = "Editing"
            dialog.graphic = null
            dialog.dialogPane.content = inputs
            dialog.dialogPane.styleClass.add("tileDialog")
            dialog.initOwner(stage)
            val result = dialog.showAndWait()
            return if (result.isPresent) {
                val w = valueOfWithDefault(width.text, 16)
                val h = valueOfWithDefault(height.text, 16)
                val e = Entity(type.value, x, y, w, h)
                e.hitbox
            } else {
                return null
            }
        }
    }

}
