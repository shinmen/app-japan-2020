package fr.jbouffard.japan2020.Domain.Travel.Entity

import fr.jbouffard.japan2020.Domain.AggregateRoot
import fr.jbouffard.japan2020.Domain.Travel.Event.*
import fr.jbouffard.japan2020.Domain.Travel.Exception.HolidayTooExpensiveException
import fr.jbouffard.japan2020.Domain.Travel.Exception.NotEnoughTimeToPlanException
import fr.jbouffard.japan2020.Domain.Travel.ValueObject.Flight
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

    override fun applyEvent(event: Event, isNew: Boolean) {
        super.applyEvent(event, isNew)
        load(event)
    }

    fun selectRoundTrip(goingFlight: Flight, returnFlight: Flight) {
        val goingDepartureDate = DateTime(goingFlight.departureDate).plus(Period.days(15))
        if (goingDepartureDate.isAfterNow) {
            throw NotEnoughTimeToPlanException("la date de départ est trop proche pour s'organiser")
        }

        val returnArrivalDate = DateTime(returnFlight.arrivalDate)
        goingDepartureDate.plus(Period.days(14))
        if (goingDepartureDate.isBefore(returnArrivalDate)) {
            throw HolidayTooExpensiveException("Nous n'aurons pas assez de tune pour un voyage si long")
        }
        val flightPlan = FlightPlan(goingFlight, returnFlight)
        //applyChange(FlightPlanSelected(flightPlan, version, uuid))
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

    private fun load(event: Event) = when(event) {
        is FlightPlanSelected -> loadEvent(event)
        is FlyToJapan -> loadEvent(event)
        is ReturnFlightScheduled -> loadEvent(event)
        is ArrivedInJapan -> loadEvent(event)
    }
    /*private inline fun <reified T: Event> load(event: Event)  {
        loadEvent(event as T)
    }*/

    fun loadEvent(event: FlightPlanSelected) {
        version++
    }

    fun loadEvent(event: ReturnFlightScheduled) {
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