package fr.jbouffard.japan2020.View.PlanHoliday

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import fr.jbouffard.japan2020.R


class StartHolidayPlanningFragment : Fragment() {

    private var mListener: OnStartHolidayPlanningFlightPlanListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_start_holiday_planning, container, false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnStartHolidayPlanningFlightPlanListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnStartHolidayPlanningFlightPlanListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }


    interface OnStartHolidayPlanningFlightPlanListener {
        fun onStartHolidayPlanning()
    }

    companion object {
        fun newInstance(): StartHolidayPlanningFragment {
            val args = Bundle().apply {
            }
            return StartHolidayPlanningFragment().apply { arguments = args }
        }
    }
}// Required empty public constructor
