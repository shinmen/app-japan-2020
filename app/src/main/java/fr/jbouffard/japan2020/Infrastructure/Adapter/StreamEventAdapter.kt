package fr.jbouffard.japan2020.Infrastructure.Adapter

import com.google.gson.*
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import fr.jbouffard.japan2020.Domain.Travel.Event.StreamEvent
import fr.jbouffard.japan2020.Domain.Travel.Entity.FlightPlan
import fr.jbouffard.japan2020.Domain.Travel.Event.FlightPlanSelected
import fr.jbouffard.japan2020.Infrastructure.Repository.ESEvent
import java.lang.reflect.Type
import com.google.gson.GsonBuilder
import fr.jbouffard.japan2020.Domain.Travel.ValueObject.Flight
import com.google.gson.reflect.TypeToken
import com.google.gson.Gson






/**
 * Created by julienb on 01/03/18.
 */
class StreamEventAdapter : JsonDeserializer<ESEvent> {
    private val mGson = Gson()
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): ESEvent? {
        val jsonObject = json?.asJsonObject
        val uuid = jsonObject?.get("eventId")?.asString
        val type = jsonObject?.get("eventType")?.asString
        val data = jsonObject?.get("data")?.asJsonObject
        val javaclass = when(type) {
            "FlightPlanSelected" -> FlightPlanSelected::class.java
            else -> {
                null
            }
        }
        val gsonBldr = GsonBuilder()
        val streamEvent = gsonBldr.create().fromJson(data, javaclass)

        return ESEvent(uuid!!, type!!, streamEvent)
    }
/*

    override fun write(jsonWriter: JsonWriter?, event: ESEvent?) {
        mGson.toJson(event, StreamEvent::class.java, jsonWriter)
    }
*/

/*    override fun read(jsonReader: JsonReader?): ESEvent? {
        jsonReader?.toString()
        jsonReader?.beginArray()

        jsonReader?.skipValue()

        jsonReader?.endArray()
        return null
        //return mGson.fromJson(jsonReader, FlightPlanSelected::class.java) as FlightPlanSelected
    }*/
}

/*//val t = mGson.fromJson(json, FlightPlanSelected::class.java)
        //val collectionType = object : TypeToken<FlightPlanSelected>(){}.type as Type
        /*//*val type = object: TypeToken<FlightPlan>(){}.type
        val gsonBldr = GsonBuilder()
        val data = jsonObject?.getAsJsonObject("data")
        val flightPlanSelected = data?.getAsJsonObject("flightPlanSelected")
        val flightPlan = gsonBldr.create().fromJson(flightPlanSelected?.getAsJsonObject("flightPlan"), type) as FlightPlan
        val res = gsonBldr.create().fromJson(data, FlightPlanSelected::class.java)
        return res
        //return context?.deserialize(jsonObject, FlightPlanSelected::class.java)*//**//**/

        val gson = Gson()
        val temp = gson.fromJson(json, FlightPlanSelected::class.java)
        return temp*/