package fr.jbouffard.japan2020.Domain.Travel.Entity

import fr.jbouffard.japan2020.Domain.AggregateRoot
import fr.jbouffard.japan2020.Domain.Travel.Event.*
import fr.jbouffard.japan2020.Domain.DomainEvent
import fr.jbouffard.japan2020.Domain.Travel.Exception.NotEnoughTimeToPlanException
import fr.jbouffard.japan2020.Domain.Travel.ValueObject.Overnight
import fr.jbouffard.japan2020.Domain.Travel.ValueObject.RailpassPackage
import fr.jbouffard.japan2020.Domain.Travel.ValueObject.Visit
import org.joda.time.DateTime
import org.joda.time.Period
import java.util.*

/**
 * Created by julienb on 26/02/18.
 */
class Holiday(override var uuid: UUID) : AggregateRoot() {
    private var flightPlan: FlightPlan? = null
    private var daySchedules: MutableList<Day> = mutableListOf()
    private var railpassPackage: RailpassPackage? = null

    override fun applyNewEvent(domainEvent: DomainEvent) {
        super.applyNewEvent(domainEvent)
        load(domainEvent as EventList)
    }

    override fun applyEvent(domainEvent: DomainEvent) {
        load(domainEvent as EventList)
    }

    fun selectRoundTrip(flightPlan: FlightPlan) {
        val goingDepartureDate = DateTime(flightPlan.goingFlight.departureDate)
        val soonDate = DateTime().apply { plus(Period.days(15))}
        if (soonDate.isAfter(goingDepartureDate)) {
            throw NotEnoughTimeToPlanException("la date de départ est trop proche pour s'organiser")
        }

        applyNewEvent(FlyToJapan(flightPlan.goingFlight, version, uuid))
        applyNewEvent(FlyBackToFrance(flightPlan.returnFlight, version, uuid))
    }

    fun selectRailPassPackage(railpass: RailpassPackage) {

    }

    fun wakeUp() {

    }

    fun scheduleVisitCity(visit: Visit) {

    }

    fun scheduleStayOver(stay: Overnight) {

    }

    fun goToCity() {

    }

    private fun load(event: EventList) = when(event) {
        is FlyToJapan -> loadEvent(event)
        is FlyBackToFrance -> loadEvent(event)
        is ArrivedInJapan -> loadEvent(event)
    }

    fun loadEvent(event: FlyBackToFrance) {
        version++
    }

    fun loadEvent(event: FlyToJapan) {
        //flightPlan = event.goingFlight
        version++
    }

    fun loadEvent(event: ArrivedInJapan) {
        version++
    }
}