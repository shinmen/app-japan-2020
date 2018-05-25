package fr.jbouffard.japan2020.Domain.Budget.Event


import fr.jbouffard.japan2020.Domain.Budget.ValueObject.FlightPlan
import fr.jbouffard.japan2020.Domain.DomainEvent
import java.util.*

/**
 * Created by julienb on 26/02/18.
 */
sealed class EventList
data class FlightPlanProvisioned(val flightPlan: FlightPlan, val version: Int, override val id: UUID): EventList(), DomainEvent