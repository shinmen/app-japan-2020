package fr.jbouffard.japan2020.Infrastructure.Repository

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import fr.jbouffard.japan2020.Domain.Travel.Event.FlightPlanSelected
import fr.jbouffard.japan2020.Domain.Travel.Event.StreamEvent

/**
 * Created by julienb on 01/03/18.
 */
data class ESEvent(
        @SerializedName("eventId") val eventId: String,
        @SerializedName("eventType") val eventType: String,
        @Expose @SerializedName("data") val data: StreamEvent
)