package fr.jbouffard.japan2020.Domain.Travel.Entity

import fr.jbouffard.japan2020.Domain.AggregateRoot
import fr.jbouffard.japan2020.Domain.Travel.Event.*
import fr.jbouffard.japan2020.Domain.DomainEvent
import fr.jbouffard.japan2020.Domain.Travel.ValueObject.Overnight
import fr.jbouffard.japan2020.Domain.Travel.ValueObject.RailpassPackage
import fr.jbouffard.japan2020.Domain.Travel.ValueObject.Visit
import org.joda.time.DateTime
import org.joda.time.Duration
import java.util.*

/**
 * Created by julienb on 26/02/18.
 */
class Holiday(override var uuid: UUID) : AggregateRoot() {
    override val streamId: String by lazy { "Holiday-$uuid" }
    private var airTransportation: AirTransportation? = null
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

    fun selectRoundTrip(goingFlightPlan: FlightPlan, returnFlightPlan: FlightPlan, fare: Float) {
        val airTransportation = AirTransportation(goingFlightPlan, returnFlightPlan, fare)
        airTransportation.selectRoundTrip()

        applyNewEvent(SelectFlightPlan(goingFlightPlan, returnFlightPlan, fare, version, streamId))
        applyNewEvent(PlanHolidayPeriod(
                goingFlightPlan.flightPlan.last().arrivalDate,
                returnFlightPlan.flightPlan.first().departureDate,
                version,
                streamId
            )
        )
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
        is SelectFlightPlan -> loadEvent(event)
        is PlanHolidayPeriod -> loadEvent(event)
        is ArrivedInJapan -> loadEvent(event)
    }

    private fun loadEvent(event: SelectFlightPlan) {
        airTransportation = AirTransportation(event.goingFlightPlan, event.returnFlightPlan, event.fare)
        version++
    }

    private fun loadEvent(event: PlanHolidayPeriod) {
        startHolidayAt = event.holidayStartAt
        endHolidayAt = event.holidayEndAt
        version++
    }

    private fun loadEvent(event: ArrivedInJapan) {
        daySchedules.add(Day())
        version++
    }
}