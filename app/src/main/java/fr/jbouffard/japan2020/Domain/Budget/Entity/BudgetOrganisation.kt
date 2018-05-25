package fr.jbouffard.japan2020.Domain.Budget.Entity

import fr.jbouffard.japan2020.Domain.AggregateRoot
import fr.jbouffard.japan2020.Domain.Budget.Event.EventList
import fr.jbouffard.japan2020.Domain.Budget.Event.FlightPlanProvisioned
import fr.jbouffard.japan2020.Domain.Budget.Exception.HolidayTooExpensiveException
import fr.jbouffard.japan2020.Domain.Budget.ValueObject.FlightPlan
import fr.jbouffard.japan2020.Domain.DomainEvent
import org.joda.time.DateTime
import org.joda.time.Period
import java.util.*

/**
 * Created by julienb on 15/05/18.
 */
class BudgetOrganisation(override var uuid: UUID) : AggregateRoot() {

    fun provisionRoundTrip(flightPlan: FlightPlan) {
        val returnArrivalDate = DateTime(flightPlan.returnFlight.arrivalDate)
        val goingDepartureDate = DateTime(flightPlan.goingFlight.departureDate)

        goingDepartureDate.plus(Period.days(14))
        if (goingDepartureDate.isAfter(returnArrivalDate)) {
            throw HolidayTooExpensiveException("Nous n'aurons pas assez de tune pour un voyage si long")
        }

        applyNewEvent(FlightPlanProvisioned(flightPlan, version, uuid))
    }


    override fun applyNewEvent(domainEvent: DomainEvent) {
        super.applyNewEvent(domainEvent)
        load(domainEvent as EventList)
    }

    override fun applyEvent(domainEvent: DomainEvent) {
        load(domainEvent as EventList)
    }

    private fun load(event: EventList) = when(event) {
        is FlightPlanProvisioned -> loadEvent(event)
    }

    fun loadEvent(event: FlightPlanProvisioned) {
        version++
    }
}