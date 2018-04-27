package fr.jbouffard.japan2020.View.PlanHoliday

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
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
        private const val ARG_DATE = "date"
        private const val ARG_VIEW = "view"

        fun newInstance(date: DateTime, view: Int): DatePickerDialogFragment {
            val args = Bundle().apply {
                putSerializable(ARG_DATE, date)
                putInt(ARG_VIEW, view)
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
        val v = arguments!!.getInt(ARG_VIEW)
        val timeFragment = TimePickerDialogFragment.newInstance(date, v)
        timeFragment.fragmentListener = fragmentListener
        timeFragment.show(fragmentManager, "time")
    }
}