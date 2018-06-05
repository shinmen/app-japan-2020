package fr.jbouffard.japan2020.Domain.Travel.Entity

import fr.jbouffard.japan2020.Domain.AggregateRoot
import fr.jbouffard.japan2020.Domain.Travel.Event.*
import fr.jbouffard.japan2020.Domain.DomainEvent
import fr.jbouffard.japan2020.Domain.Travel.Exception.NotEnoughTimeToPlanException
import fr.jbouffard.japan2020.Domain.Travel.ValueObject.Flight
import fr.jbouffard.japan2020.Domain.Travel.ValueObject.Overnight
import fr.jbouffard.japan2020.Domain.Travel.ValueObject.RailpassPackage
import fr.jbouffard.japan2020.Domain.Travel.ValueObject.Visit
import org.joda.time.DateTime
import org.joda.time.Duration
import org.joda.time.Period
import java.util.*

/**
 * Created by julienb on 26/02/18.
 */
class Holiday(override var uuid: UUID) : AggregateRoot() {
    override val streamId: String by lazy { "Holiday-$uuid" }
    private var goingFlight: Flight? = null
    private var returnFlight: Flight? = null
    private var daySchedules: MutableList<Day> = mutableListOf()
    private var railpassPackage: RailpassPackage? = null
    private var startHolidayAt: DateTime? = null
    private var endHolidayAt: DateTime? = null

    var holidayDuration: Long = 0
        get() = Duration(startHolidayAt, endHolidayAt).standardDays

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

        applyNewEvent(FlyToJapan(flightPlan.goingFlight, version, streamId))
        applyNewEvent(FlyBackToFrance(flightPlan.returnFlight, version, streamId))
    }

    fun startHolidayPlanning() {
        applyNewEvent(ArrivedInJapan(version, streamId))
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

    private fun loadEvent(event: FlyBackToFrance) {
        returnFlight = event.returnFlight
        endHolidayAt = DateTime(event.returnFlight.departureDate.time)
        version++
    }

    private fun loadEvent(event: FlyToJapan) {
        goingFlight = event.goingFlight
        startHolidayAt = DateTime(event.goingFlight.arrivalDate.time)
        version++
    }

    private fun loadEvent(event: ArrivedInJapan) {
        daySchedules.add(Day())
        version++
    }
}