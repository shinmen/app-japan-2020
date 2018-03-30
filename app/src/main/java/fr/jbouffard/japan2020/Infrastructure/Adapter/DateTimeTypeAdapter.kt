package fr.jbouffard.japan2020.Infrastructure.Adapter

import org.joda.time.DateTime
import com.google.gson.JsonParseException
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonSerializer
import org.joda.time.format.DateTimeFormat
import java.lang.reflect.Type


/**
 * Created by julienb on 29/03/18.
 */
internal class DateTimeTypeAdapter : JsonSerializer<DateTime>, JsonDeserializer<DateTime> {
    // No need for an InstanceCreator since DateTime provides a no-args constructor
    override fun serialize(src: DateTime, srcType: Type, context: JsonSerializationContext): JsonElement {
        return JsonPrimitive(src.toString())
    }

    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, type: Type, context: JsonDeserializationContext): DateTime {
        val format = DateTimeFormat.forPattern("y-M-d H:m")
        return format.parseDateTime(json.asString)
    }
}