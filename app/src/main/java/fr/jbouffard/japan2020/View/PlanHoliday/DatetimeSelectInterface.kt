package fr.jbouffard.japan2020.View.PlanHoliday

import org.joda.time.DateTime

/**
 * Created by julienb on 19/03/18.
 */
interface DatetimeSelectInterface {
    fun onDatetimeSelected(dateTime: DateTime, view: Int)
}