package fr.jbouffard.japan2020.View.PlanHoliday

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.widget.TimePicker
import fr.jbouffard.japan2020.R
import org.joda.time.DateTime
import java.util.*

/**
 * Created by julienb on 15/03/18.
 */
class TimePickerDialogFragment: DialogFragment(),
        TimePickerDialog.OnTimeSetListener
{
    lateinit var fragmentListener: DatetimeSelectInterface
    lateinit var date: DateTime

    companion object {
        private val ARG_TIME = "time"

        fun newInstance(date: DateTime): TimePickerDialogFragment {
            val args = Bundle().apply { putSerializable(ARG_TIME, date) }

            return TimePickerDialogFragment().apply { arguments = args }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        date = arguments!!.getSerializable(ARG_TIME) as DateTime
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AppTheme)

        // Create a new instance of TimePickerDialog and return it
        return TimePickerDialog(activity, this, date.hourOfDay, date.minuteOfHour, true)
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        val datetime = DateTime(date.year, date.monthOfYear, date.dayOfMonth, hourOfDay, minute)

        fragmentListener.onDatetimeSelected(datetime)
    }
}