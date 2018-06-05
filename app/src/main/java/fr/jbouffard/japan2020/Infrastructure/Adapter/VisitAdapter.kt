package fr.jbouffard.japan2020.Infrastructure.Adapter

import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.mapbox.mapboxsdk.geometry.LatLng
import fr.jbouffard.japan2020.Domain.Travel.ValueObject.City
import fr.jbouffard.japan2020.Infrastructure.DTO.Visit
import fr.jbouffard.japan2020.Infrastructure.Repository.EventDescription
import java.lang.reflect.Type
import java.util.*

/**
 * Created by julienb on 04/06/18.
 */
class VisitAdapter : JsonDeserializer<Visit> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Visit? {
        val jsonObject = json?.asJsonObject ?: return null
        val latLng = LatLng(jsonObject.get("latitude").asDouble, jsonObject.get("longitude").asDouble)

        return Visit(jsonObject.get("name").asString, latLng, jsonObject.get("tourism_info").asString)
    }
}