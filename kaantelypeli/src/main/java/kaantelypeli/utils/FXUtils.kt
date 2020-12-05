package kaantelypeli.utils

import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.control.Button
import javafx.scene.control.ChoiceDialog
import javafx.scene.image.Image
import javafx.scene.paint.ImagePattern
import javafx.scene.shape.Rectangle
import javafx.stage.Stage
import kong.unirest.Unirest
import org.tinylog.Logger

/**
 * Create a JavaFX button.
 * @param name Name and Id for the button
 * @param action Action to call on button press
 * @return Button with the given options
 */
fun createButton(
    name: String?,
    callback: (ActionEvent) -> Unit
): Button {
    val button = Button(name)
    val action = EventHandler<ActionEvent> { event -> callback(event) }
    button.onAction = action
    button.id = name
    return button
}

/**
 * Create a list selector for a JSON list retrieved from URL.
 * @param url list URL
 * @param stage stage to parent this choice dialog
 * @return ChoiceDialog with the given options
 */
fun selector(url: String, stage: Stage?): ChoiceDialog<String> {
    val choice = ChoiceDialog<String>()
    choice.initOwner(stage)
    choice.headerText = "Level to load"
    choice.graphic = null
    choice.title = null
    Logger.trace("GETing: {}", url)
    val options = Unirest.get(url).asJson().body.array.toList()
    for (i in options) {
        choice.items.add(i.toString())
    }
    return choice
}

fun IntRectangle(i: Int, i1: Int, i2: Int, i3: Int): Rectangle {
    return Rectangle(i.toDouble(), i1.toDouble(), i2.toDouble(), i3.toDouble())
}

fun IntImagePattern(sprite: Image, i: Int, i1: Int, i2: Int, i3: Int, b: Boolean): ImagePattern {
    return ImagePattern(sprite, i.toDouble(), i1.toDouble(), i2.toDouble(), i3.toDouble(), b)

}
