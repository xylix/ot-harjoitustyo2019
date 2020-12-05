package kaantelypeli.engine

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
        val data: EntityData = jdc.deserialize(je, EntityData::class.java)
        val entity: Entity
        entity = if (data.width == null || data.height == null) {
            Entity(data.type, data.x, data.y)
        } else {
            Entity(data.type, data.x, data.y, data.width!!, data.height!!)
        }
        entity.setProperties()
        return entity
    }

    private inner class EntityData {
        var type: String? = null
        var x = 0
        var y = 0
        var width: Int? = null
        var height: Int? = null
    }
}
