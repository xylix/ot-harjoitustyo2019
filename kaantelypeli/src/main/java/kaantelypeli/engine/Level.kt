package kaantelypeli.engine

import com.google.gson.GsonBuilder
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import org.tinylog.kotlin.Logger
import java.util.*
import java.util.function.Consumer
import java.util.stream.Collectors

/**
 * Handles level generation and level management.
 * Exposes loadLevel static method for level generation and
 * gravitate() method for binding gravitation to game ticks.
 */
class Level private constructor() {
    var gravity: Int
    var won: Boolean
    var lost: Boolean
    private var entities: List<Entity>

    init {
        entities = ArrayList()
        gravity = 0
        won = false
        lost = false
    }

    /**
     * Creates level from entity list.
     * LevelEditor uses this.
     * @param entities list of entities to place on level
     */
    constructor(entities: List<Entity>) : this() {
        this.entities = entities
    }

    /**
     * When level has been won, perform action.
     * @param action Action to call when level has been won
     */
    fun ifOver(action: Runnable) {
        if (won) {
            action.run()
        }
    }

    /**
     * Changes the gravity by `degrees`.
     *
     * @param   degrees   Wanted change in degrees
     */
    fun changeGravity(degrees: Int) {
        gravity += degrees
    }

    /**
     * Method to externally bind this level to game ticks. Should get called every tick when level is active.
     */
    fun tick() {
        if (won) {
            return
        } else if (lost) {
            restart()
            return
        }
        gravitate()
    }

    fun restart() {
        entities.forEach(Consumer { e: Entity ->
            e.hitbox!!.translateX = 0.0
            e.hitbox!!.translateY = 0.0
        })
        lost = false
    }

    /** Applies gravity to all entities on the level. Movables get moved here.  */
    private fun gravitate() {
        entities.stream()
            .filter { e: Entity -> e.movable }
            .forEach { collider: Entity ->
                collider.move(gravity)
                entities.stream()
                    .filter { collidee: Entity ->
                        collider.collide(collidee)
                    }.forEach { collidee: Entity ->
                        val action = collider.collisionAction(collidee)
                        handleAction(collider, collidee, action)
                    }
            }
    }

    /** Resolves Entity interaction.  */
    private fun handleAction(
        collider: Entity,
        collidee: Entity,
        action: String?
    ) {
        if (!action!!.isBlank()) {
            Logger.trace(action)
        }
        if ("victory" == action) {
            println("You're winner!")
            won = true
        } else if ("loss" == action) {
            lost = true
        } else if ("open" == action) {
            collidee.passable = true
            collidee.hitbox!!.fill = Color.TRANSPARENT
            collider.movable = false
            collider.hitbox!!.fill = Color.TRANSPARENT
        } else if (!collidee.passable) {
            collider.move(gravity + 540)
        }
    }

    /**
     * Converts the level to JSON.
     * @return JSON representation of the level
     */
    val json: String
        get() {
            val gson = GsonBuilder().setPrettyPrinting().create()
            return gson.toJson(this)
        }

    val hitboxes: List<Rectangle?>
        get() = entities
            .map { e: Entity -> e.hitbox }


    fun getEntities(): List<Entity> {
        return Collections.unmodifiableList(entities)
    }

}
