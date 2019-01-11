package fr.jbouffard.japan2020.View.PlanHoliday.Budget

import org.joda.time.DateTime

data class BudgetDay(private val dayNb: Long): ViewType {

    fun getDayNb(): DateTime = DateTime().withMillis(dayNb)

    override fun getViewType(): Int = VIEW_TYPE

    companion object {
        const val VIEW_TYPE = 2
    }
}