package fr.jbouffard.japan2020.Infrastructure.Adapter

import fr.jbouffard.japan2020.Domain.Travel.Entity.Day
import fr.jbouffard.japan2020.Domain.Travel.Entity.Holiday
import fr.jbouffard.japan2020.Domain.Travel.ValueObject.Movement
import fr.jbouffard.japan2020.Domain.Travel.ValueObject.Overnight
import fr.jbouffard.japan2020.Domain.Travel.ValueObject.RailpassPackage
import fr.jbouffard.japan2020.View.PlanHoliday.DetailPlan.DetailRailpassPackage
import fr.jbouffard.japan2020.View.PlanHoliday.DetailPlan.OvernightDaySchedule
import fr.jbouffard.japan2020.View.PlanHoliday.DetailPlan.VisitDaySchedule
import fr.jbouffard.japan2020.View.PlanHoliday.ViewType

class DetailDayAdapter {

    fun toDetailDays(days : List<Day>): List<ViewType> {
        return days
                .flatMap { day->
                    val lines = mutableListOf<ViewType>()
                    lines.addAll(day.movements.map { visit -> toVisit(visit) })
                    lines.add(toOvernight(day.overnight!!))
                    lines
                }
    }

    fun toRailpassDay(holiday: Holiday): List<ViewType> {
        val railPass = holiday.getRailPass()

        return if(railPass == null) {
            listOf()
        } else {
            listOf(toRailpass(railPass))
        }
    }

    private fun toVisit(visit: Movement): ViewType {
        return VisitDaySchedule(visit)
    }

    private fun toOvernight(overnight: Overnight): ViewType {
        return OvernightDaySchedule(overnight)
    }

    private fun toRailpass(railpass: RailpassPackage): ViewType {
        return DetailRailpassPackage(railpass)
    }
}