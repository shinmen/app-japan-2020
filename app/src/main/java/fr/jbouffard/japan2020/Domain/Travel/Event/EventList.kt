package fr.jbouffard.japan2020.Domain.Travel.Event


import fr.jbouffard.japan2020.Domain.DomainEvent
import fr.jbouffard.japan2020.Domain.Travel.Entity.FlightPlan
import fr.jbouffard.japan2020.Domain.Travel.ValueObject.City
import fr.jbouffard.japan2020.Domain.Travel.ValueObject.Flight
import fr.jbouffard.japan2020.Domain.Travel.ValueObject.Movement
import org.joda.time.DateTime
import java.util.*

/**
 * Created by julienb on 26/02/18.
 */
sealed class EventList
data class PlanHolidayPeriod(val holidayStartAt: DateTime, val holidayEndAt: DateTime, val version: Int, override val streamId: String): EventList(), DomainEvent
data class SelectFlightPlan(val goingFlightPlan: FlightPlan, val returnFlightPlan: FlightPlan, val fare: Float, val version: Int, override val streamId: String): EventList(), DomainEvent
data class ArrivedInJapan(val arrivedAt: DateTime, val firstCity: City, val version: Int, override val streamId: String): EventList(), DomainEvent
data class NewDayStarted(val date: DateTime, val version: Int, override val streamId: String): EventList(), DomainEvent
data class MovedToCity(val move: Movement, val version: Int, override val streamId: String): EventList(), DomainEvent

