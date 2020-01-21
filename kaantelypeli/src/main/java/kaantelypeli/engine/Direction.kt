package kaantelypeli.engine

import javafx.util.Pair
import java.lang.Math.toRadians
import kotlin.math.cos
import kotlin.math.sin

class Direction(var x: Double, var y: Double) {
    fun rotate(degrees: Int) {
        val radians = toRadians(degrees.toDouble())
        val deltaVector = Pair(cos(radians), sin(radians))
        x += deltaVector.key
        y += deltaVector.value
    }

    fun reverse(): Direction {
        return Direction(x * -1, y * -1)
    }

    fun add(d: Direction) {
        this.x += d.x
        this.y += d.y
    }

    override fun toString(): String {
        return "{$x}, {$y}"
    }
}
