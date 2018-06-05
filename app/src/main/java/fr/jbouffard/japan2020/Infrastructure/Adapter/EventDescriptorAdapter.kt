package fr.jbouffard.japan2020.Infrastructure.Adapter

import com.google.gson.*
import fr.jbouffard.japan2020.Infrastructure.Repository.EventDescription
import java.lang.reflect.Type
import com.google.gson.GsonBuilder
import fr.jbouffard.japan2020.Domain.Budget.Event.FlightPlanProvisioned
import fr.jbouffard.japan2020.Domain.DomainEvent
import fr.jbouffard.japan2020.Domain.Travel.Event.ArrivedInJapan
import fr.jbouffard.japan2020.Domain.Travel.Event.PlanHolidayPeriod
import fr.jbouffard.japan2020.Domain.Travel.Event.SelectFlightPlan
import java.util.*


/**
 * Created by julienb on 01/03/18.
 */
class EventDescriptorAdapter : JsonDeserializer<EventDescription> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): EventDescription? {
        val jsonObject = json?.asJsonObject ?: return null
        if (!jsonObject.has("eventType")) {
            throw RuntimeException("cannot deserialize event without type")
        }
        val uuid = UUID.fromString(jsonObject.get("eventId")?.asString)
        val type = jsonObject.get("eventType")?.asString
        val data = jsonObject.get("data")?.asJsonObject
        val javaclass = when(type) {
            "SelectFlightPlan" -> SelectFlightPlan::class.java
            "PlanHolidayPeriod" -> PlanHolidayPeriod::class.java
            "ArrivedInJapan" -> ArrivedInJapan::class.java
            else -> {
                null
            }
        }
        val gson = GsonBuilder().setDateFormat("yyyy-M-dd hh:mm:ss")
        val streamEvent = gson.create().fromJson(data, javaclass) as DomainEvent

        return EventDescription(uuid, type!!, streamEvent)
    }
}