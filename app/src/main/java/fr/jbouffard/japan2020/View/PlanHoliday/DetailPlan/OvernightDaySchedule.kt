package fr.jbouffard.japan2020.View.PlanHoliday.DetailPlan

import fr.jbouffard.japan2020.Domain.Travel.ValueObject.Overnight
import fr.jbouffard.japan2020.View.PlanHoliday.ViewType
import org.joda.time.DateTime

class OvernightDaySchedule(val overnight: Overnight): ViewType {
    override fun getDate(): Long {
        return DateTime(overnight.overnightDate).withTimeAtStartOfDay().millis
    }

    override fun getViewType(): Int {
        return VIEW_TYPE
    }

    companion object {
        const val VIEW_TYPE = 5
    }
}