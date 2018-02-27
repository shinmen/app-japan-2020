package fr.jbouffard.japan2020.Domain.Travel.Entity

import fr.jbouffard.japan2020.Domain.AggregateRoot
import fr.jbouffard.japan2020.Domain.Travel.Event.AppEvent
import fr.jbouffard.japan2020.Domain.Travel.Event.FlightPlanSelected
import fr.jbouffard.japan2020.Domain.Travel.ValueObject.Flight
import fr.jbouffard.japan2020.Domain.Travel.ValueObject.Overnight
import fr.jbouffard.japan2020.Domain.Travel.ValueObject.RailpassPackage
import fr.jbouffard.japan2020.Domain.Travel.ValueObject.Visit
import org.joda.time.DateTime
import org.joda.time.Period

/**
 * Created by julienb on 26/02/18.
 */
class Holiday : AggregateRoot() {
    private var flightPlan: FlightPlan? = null
    private var daySchedules: MutableList<Day> = mutableListOf()
    private var railpassPackage: RailpassPackage? = null

    override fun applyEvent(event: AppEvent, isNew: Boolean) {
        super.applyEvent(event, isNew)
        load(event)
    }

    fun selectRoundTrip(goingFlight: Flight, returnFlight: Flight) {
        val calGoing = DateTime(goingFlight.departureDate).plus(Period.days(15))
        if (calGoing.isAfterNow) {
            throw Exception("la date de départ est trop proche")
        }

        val calReturn = DateTime(returnFlight.arrivalDate)
        calGoing.plus(Period.days(14))
        if (calReturn.isAfter(calReturn)) {
            throw Exception("Nous n'aurons pas assez de tune pour un voyage si long")
        }
        val flightPlan = FlightPlan(goingFlight, returnFlight)
        applyChange(FlightPlanSelected(flightPlan, version))
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

    private fun load(event: AppEvent) = when(event) {
        is FlightPlanSelected -> loadEvent(event)
    }

    private fun loadEvent(event: FlightPlanSelected) {
        flightPlan = event.flightPlan
        version++
    }
}