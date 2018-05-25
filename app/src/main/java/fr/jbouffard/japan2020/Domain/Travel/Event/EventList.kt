package fr.jbouffard.japan2020.Domain.Travel.Event


import fr.jbouffard.japan2020.Domain.DomainEvent
import fr.jbouffard.japan2020.Domain.Travel.ValueObject.Flight
import java.util.*

/**
 * Created by julienb on 26/02/18.
 */
sealed class EventList
data class FlyToJapan(val goingFlight: Flight, val version: Int, override val id: UUID): EventList(), DomainEvent
data class FlyBackToFrance(val returnFlight: Flight, val version: Int, override val id: UUID): EventList(), DomainEvent
data class ArrivedInJapan(val id: UUID): EventList()