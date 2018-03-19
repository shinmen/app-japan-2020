package fr.jbouffard.japan2020.View.PlanHoliday

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.View
import android.widget.DatePicker
import fr.jbouffard.japan2020.R
import org.joda.time.DateTime

/**
 * Created by julienb on 15/03/18.
 */
class DatePickerDialogFragment : DialogFragment(),
        DatePickerDialog.OnDateSetListener
{
    lateinit var fragmentListener: DatetimeSelectInterface

    companion object {
        private val ARG_DATE = "date"

        fun newInstance(date: DateTime): DatePickerDialogFragment {
            val args = Bundle().apply {
                putSerializable(ARG_DATE, date)
            }

            return DatePickerDialogFragment().apply { arguments = args }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val date = arguments!!.getSerializable(ARG_DATE) as DateTime
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AppTheme)
        // Create a new instance of DatePickerDialog and return it
        return DatePickerDialog(
                activity,
                this,
                date.year,
                date.monthOfYear - 1,
                date.dayOfMonth
        )
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val date = DateTime().withDate(year, month + 1, dayOfMonth)
        val timeFragment = TimePickerDialogFragment.newInstance(date)
        timeFragment.fragmentListener = fragmentListener
        timeFragment.show(fragmentManager, "time")
    }
}