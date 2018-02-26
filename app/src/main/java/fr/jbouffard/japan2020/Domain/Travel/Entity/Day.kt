package fr.jbouffard.japan2020.Domain.Travel.Entity

import fr.jbouffard.japan2020.Domain.Travel.ValueObject.Overnight
import fr.jbouffard.japan2020.Domain.Travel.ValueObject.Visit

/**
 * Created by julienb on 26/02/18.
 */
class Day {
    var overnight: Overnight? = null
        private set
    var visits: MutableList<Visit> = mutableListOf()

    fun scheduleAccomodation(overnight: Overnight) {
        this.overnight = overnight
    }

    fun scheduleVisit(visit: Visit) {
        visits.add(visit)
    }
}