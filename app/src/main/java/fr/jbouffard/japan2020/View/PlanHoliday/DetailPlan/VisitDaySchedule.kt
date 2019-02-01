package fr.jbouffard.japan2020.View.PlanHoliday.DetailPlan

import fr.jbouffard.japan2020.Domain.Travel.ValueObject.Movement
import fr.jbouffard.japan2020.Infrastructure.DTO.Visit
import fr.jbouffard.japan2020.View.PlanHoliday.ViewType
import org.joda.time.DateTime

class VisitDaySchedule(val visit: Movement): ViewType {
    override fun getDate(): Long {
        return visit.date.withTimeAtStartOfDay().millis
    }

    override fun getViewType(): Int {
        return VIEW_TYPE
    }

    companion object {
        const val VIEW_TYPE = 4
    }
}