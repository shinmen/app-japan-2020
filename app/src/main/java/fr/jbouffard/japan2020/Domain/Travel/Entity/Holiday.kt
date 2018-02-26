package fr.jbouffard.japan2020.Domain.Travel.Entity

import fr.jbouffard.japan2020.Domain.AggregateRoot
import fr.jbouffard.japan2020.Domain.Travel.Event.AppEvent
import fr.jbouffard.japan2020.Domain.Travel.Event.Test
import fr.jbouffard.japan2020.Domain.Travel.Event.Test2
import fr.jbouffard.japan2020.Domain.Travel.ValueObject.RailpassPackage
import java.util.*

/**
 * Created by julienb on 26/02/18.
 */
class Holiday : AggregateRoot() {
    private var destinationArrivalDate: Date? = null
    private var destinationDepartureDate: Date? = null
    private var daySchedules: MutableList<Day> = mutableListOf()
    private var railpassPackage: RailpassPackage? = null

    override fun applyEvent(event: AppEvent, isNew: Boolean) {
        super.applyEvent(event, isNew)
        load(event)
    }

    fun selectFlight() {

    }

    fun selectRailPassPackage() {

    }

    fun scheduleVisitCity() {

    }

    fun scheduleStayOver() {

    }

    fun goToCity() {

    }

    private fun load(event: AppEvent) = when(event) {
        is Test -> loadEvent(event)
        is Test2 -> loadEvent(event)
    }

    private fun loadEvent(event: Test) {

    }

    private fun loadEvent(event: Test2) {

    }


}