package fr.jbouffard.japan2020.View.PlanHoliday.DetailPlan

import fr.jbouffard.japan2020.View.PlanHoliday.ViewType
import org.joda.time.DateTime

class DetailTotalPrice(val total: Float, private val endDate: DateTime): ViewType {
    override fun getDate(): Long {
        return endDate.withTimeAtStartOfDay().millis
    }

    override fun getViewType(): Int {
        return VIEW_TYPE
    }

    companion object {
        const val VIEW_TYPE = 7
    }
}