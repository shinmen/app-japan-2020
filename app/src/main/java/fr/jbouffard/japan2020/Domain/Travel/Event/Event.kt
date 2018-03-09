package fr.jbouffard.japan2020.Domain.Travel.Event


import fr.jbouffard.japan2020.Domain.Travel.Entity.FlightPlan
import java.util.*

/**
 * Created by julienb on 26/02/18.
 */
sealed class Event
class FlightPlanSelected(val flightPlan: FlightPlan, val version: Int, val id: UUID): Event()