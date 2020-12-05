package kaantelypeli.engine

import com.google.gson.Gson
import com.google.gson.annotations.JsonAdapter
import javafx.scene.shape.Rectangle
import javafx.scene.shape.Shape
import kaantelypeli.ui.SCALE
import kaantelypeli.utils.IntRectangle
import kaantelypeli.utils.loadProperties
import kaantelypeli.utils.loadSprite
import org.tinylog.kotlin.Logger
import java.util.*


import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class EntitySerializer : JsonDeserializer<Entity> {
    override fun deserialize(
        je: JsonElement,
        type: Type,
        jdc: JsonDeserializationContext
    ): Entity {
        val data: StartingPosition = jdc.deserialize(je, StartingPosition::class.java)
        val entity = Entity(data)
        return entity
    }
}
class StartingPosition {
    var type: String? = "wall"
    var x = 0
    var y = 0
    var width: Int = 16
    var height: Int = 16

    constructor(type: String?, x: Int, y: Int) {
        this.type = type;
        this.x = x;
        this.y = y;
    }

    constructor(type: String?, x: Int, y: Int, width: Int, height: Int) {
        this.type = type
        this.x = x
        this.y = y
        this.width = width
        this.height = height
    }
}

/**
 * Exposes entity generation, movement and collision.
 */
@JsonAdapter(EntitySerializer::class)
class Entity {
    private val actionMap: HashMap<String, String>

    private val start: StartingPosition

    @JvmField
    @Transient
    var hitbox: Rectangle? = null
    var movable = false
    var passable = false

    constructor(startingPosition: StartingPosition) {
        val source = setProperties()
        this.start = startingPosition
        actionMap = source.actionMap ?: HashMap()
    }

    /*
     * Sets entity properties based on entity types definition object.
     */
    fun setProperties(): Properties {
        val type = start.type
        val source = loadProperties(type!!)
        movable = source.movable
        passable = source.passable
        val _hitbox = IntRectangle(start.x * SCALE, start.y * SCALE, start.width * SCALE, start.height * SCALE)
        _hitbox.id = type
        _hitbox.fill = loadSprite("$type.png")
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
        return actionMap.get(collidee.start.type) ?: ""
    }

    fun move(i: Int) {
        val normalized = Math.abs(i % 360)
        when (normalized) {
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
        hash = 79 * hash + Objects.hashCode(start)
        hash = 79 * hash + if (movable) 1 else 0
        hash = 79 * hash + if (passable) 1 else 0
        hash = 79 * hash + actionMap.hashCode()
        return hash
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) {
            return false
        } else if (this === other) {
            return true
        } else if (javaClass != other.javaClass) {
            return false
        }
        val other = other as Entity
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
