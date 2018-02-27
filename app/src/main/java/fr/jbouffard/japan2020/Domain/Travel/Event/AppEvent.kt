package fr.jbouffard.japan2020.Domain.Travel.Event

import fr.jbouffard.japan2020.Domain.Travel.Entity.FlightPlan

/**
 * Created by julienb on 26/02/18.
 */
sealed class AppEvent
var version: Int = 0
data class FlightPlanSelected(val flightPlan: FlightPlan, var version: Int): AppEvent()