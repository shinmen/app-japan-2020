package fr.jbouffard.japan2020.View.PlanHoliday.DetailPlan

import fr.jbouffard.japan2020.Domain.Travel.ValueObject.RailpassPackage
import fr.jbouffard.japan2020.View.PlanHoliday.ViewType
import org.joda.time.DateTime

class DetailRailpassPackage(val railpassPackage: RailpassPackage): ViewType {
    override fun getViewType(): Int {
        return VIEW_TYPE
    }

    override fun getDate(): Long {
        return DateTime(railpassPackage.startDate.time).withTimeAtStartOfDay().millis
    }

    companion object {
        const val VIEW_TYPE = 8
    }
}