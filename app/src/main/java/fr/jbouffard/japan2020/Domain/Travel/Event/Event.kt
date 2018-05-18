package fr.jbouffard.japan2020.Domain.Travel.Event


import fr.jbouffard.japan2020.Domain.Travel.Entity.FlightPlan
import fr.jbouffard.japan2020.Domain.Travel.ValueObject.Flight
import java.util.*

/**
 * Created by julienb on 26/02/18.
 */
sealed class Event
data class FlightPlanSelected(val goingFlight: Flight, val returnFlight: Flight, val version: Int, val id: UUID): Event()
data class FlyToJapan(val goingFlight: Flight, val version: Int, val id: UUID): Event()
data class ReturnFlightScheduled(val returnFlight: Flight, val version: Int, val id: UUID): Event()
data class ArrivedInJapan(val id: UUID): Event()