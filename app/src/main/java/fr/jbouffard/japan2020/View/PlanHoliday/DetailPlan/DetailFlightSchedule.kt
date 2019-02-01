package fr.jbouffard.japan2020.View.PlanHoliday.DetailPlan

import fr.jbouffard.japan2020.Domain.Travel.ValueObject.Flight
import fr.jbouffard.japan2020.View.PlanHoliday.ViewType
import org.joda.time.DateTime

class DetailFlightSchedule(
        private val date: DateTime,
        val flight: Flight,
        val company: String,
        val fare: Float?
): ViewType {
    override fun getDate(): Long {
        return date.withTimeAtStartOfDay().millis
    }

    override fun getViewType(): Int {
        return VIEW_TYPE
    }

    companion object {
        const val VIEW_TYPE = 6
    }
}