package fr.jbouffard.japan2020.View.PlanHoliday

import org.joda.time.DateTime

data class DateDetailDay(private val dayNb: Long): ViewType {
    override fun getDate(): Long {
        return 0
    }

    fun getDayNb(): DateTime = DateTime().withMillis(dayNb)

    override fun getViewType(): Int = VIEW_TYPE

    companion object {
        const val VIEW_TYPE = 2
    }
}