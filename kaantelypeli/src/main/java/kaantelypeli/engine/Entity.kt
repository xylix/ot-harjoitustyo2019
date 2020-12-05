package kaantelypeli.engine

import com.google.gson.Gson
import com.google.gson.annotations.JsonAdapter
import javafx.scene.shape.Rectangle
import javafx.scene.shape.Shape
import kaantelypeli.ui.Game.Companion.SCALE
import kaantelypeli.utils.IntRectangle
import kaantelypeli.utils.loadProperties
import kaantelypeli.utils.loadSprite
import org.tinylog.kotlin.Logger
import java.util.*

/**
 * Exposes entity generation, movement and collision.
 */
@JsonAdapter(EntitySerializer::class)
class Entity {
    val type: String?
    val x: Int
    val y: Int
    private var actionMap: HashMap<String, String>? = null
    private val width: Int
    private val height: Int

    @JvmField
    @Transient
    var hitbox: Rectangle? = null
    var movable = false
    var passable = false

    /**
     * Creates a new entity of `type` at location `x`,`y` with default width and height.
     * @param type Type of new entity. See resources/entities for info.
     * @param x X-coordinate of new entity.
     * @param y Y-coordinate of new entity.
     */
    constructor(type: String?, x: Int, y: Int) {
        this.type = type
        this.x = x
        this.y = y

        val source = setProperties()
        width = source.width
        height = source.height
    }

    /**
     * Creates a new entity of `type` at location `x`,`y` with
     * non-default width and height.
     * @param type Type of new entity. See resources/entities for info.
     * @param x X-coordinate of new entity.
     * @param y Y-coordinate of new entity.
     * @param width width of new entity.
     * @param height height of new entity.
     */
    constructor(type: String?, x: Int, y: Int, width: Int, height: Int) {
        this.type = type
        this.x = x
        this.y = y
        this.width = width
        this.height = height
        setProperties()
    }

    /*
     * Sets entity properties based on entity types definition file.
     * Definition files located in entities/
     */
    fun setProperties(): Properties {
        val source = loadProperties(type!!)

        movable = source.movable
        passable = source.passable
        actionMap = source.actionMap ?: HashMap()
        val _hitbox = IntRectangle(x * SCALE, y * SCALE, width * SCALE, height * SCALE)
        _hitbox.id = type
        _hitbox.fill = loadSprite(
                source.graphics ?: "$type.png"
            )
        this.hitbox = _hitbox
        return source
    }



    /**
     * Collision check.
     * @param collidee Entity to check collision with.
     * @return returns True if `this` entity is currently colliding with `collidee`
     */
    fun collide(collidee: Entity): Boolean {
        val collisionBox = Shape.intersect(hitbox, collidee.hitbox)
        return collisionBox.boundsInLocal.width != -1.0
    }

    /**
     * Deducts what happens in a collision.
     * @param collidee Entity to collide with.
     * @return String defining what to do when `this` and `collidee` collide.
     */
    fun collisionAction(collidee: Entity): String {
        return actionMap!!.getOrDefault(collidee.type, "")
    }

    fun move(i: Int) {
        var i = i
        i = Math.abs(i % 360)
        when (i) {
            0 -> hitbox!!.translateY = hitbox!!.translateY + 1 * SCALE
            90 -> hitbox!!.translateX = hitbox!!.translateX + 1 * SCALE
            180 -> hitbox!!.translateY = hitbox!!.translateY - 1 * SCALE
            270 -> hitbox!!.translateX = hitbox!!.translateX - 1 * SCALE
            else -> Logger.error("Illegal movement call")
        }
    }

    val actualX: Double
        get() = hitbox!!.x + hitbox!!.translateX

    val actualY: Double
        get() = hitbox!!.y + hitbox!!.translateY

    override fun hashCode(): Int {
        var hash = 7
        hash = 79 * hash + Objects.hashCode(type)
        hash = 79 * hash + x
        hash = 79 * hash + y
        hash = 79 * hash + if (movable) 1 else 0
        hash = 79 * hash + if (passable) 1 else 0
        hash = 79 * hash + actionMap.hashCode()
        return hash
    }

    override fun equals(obj: Any?): Boolean {
        if (obj == null) {
            return false
        } else if (this === obj) {
            return true
        } else if (javaClass != obj.javaClass) {
            return false
        }
        val other = obj as Entity
        return json == other.json
    }

    /**
     * Converts this entity into a JSON representation.
     * @return JSON representation of the entity.
     */
    val json: String
        get() {
            val gson = Gson()
            return gson.toJson(this)
        }

}
