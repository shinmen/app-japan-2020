package fr.jbouffard.japan2020.Domain.Travel.Event

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import fr.jbouffard.japan2020.Domain.Travel.Entity.FlightPlan

/**
 * Created by julienb on 26/02/18.
 */
sealed class StreamEvent
var version: Int = 0
class FlightPlanSelected(@Expose @SerializedName("flightPlan")var flightPlan: FlightPlan, @Expose @SerializedName("version")var version: Int): StreamEvent()