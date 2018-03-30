package fr.jbouffard.japan2020.View.PlanHoliday

import android.content.Context
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.transition.*
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import fr.jbouffard.japan2020.Domain.Travel.Command.FlightRequestCommand
import fr.jbouffard.japan2020.Presenter.FlightRequestPresenter
import fr.jbouffard.japan2020.R
import kotlinx.android.synthetic.main.fragment_flightplan_list.*
import kotlinx.android.synthetic.main.fragment_start_holiday_planning.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import org.jetbrains.anko.support.v4.toast


class FlightPlanFragment : Fragment() {
    private var mFlightRequestCommand: FlightRequestCommand? = null
    private var mListener: OnListFlightPlanListener? = null
    lateinit var mPresenter: FlightRequestPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mFlightRequestCommand = it.getParcelable(ARG_FLIGHT_PLAN)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_flightplan_list, container, false)
        val loading = view.findViewById<ProgressBar>(R.id.flight_plan_loading)

        launch(UI) {
            val offers = mPresenter.requestFlightPrice(mFlightRequestCommand!!)
            val list = view.findViewById<RecyclerView>(R.id.flight_plan_list)
            val detail = view.findViewById<ConstraintLayout>(R.id.flight_plan_detailed)

            TransitionManager.beginDelayedTransition(container!!)
            loading.visibility = View.GONE
            list.visibility = View.VISIBLE
            list.apply {
                layoutManager = GridLayoutManager(activity, 2)
                adapter = FlightPlanRecyclerViewAdapter(offers) { flightOffer ->
                    toast("${flightOffer.totalRatePerAdult} Clicked")
                    if (detail.visibility == View.GONE) {
                        TransitionManager.beginDelayedTransition(container)
                        detail.visibility = View.VISIBLE
                    }
                    company_name.text = flightOffer.totalRatePerAdult.toString()
                }
            }
        }

        return view
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnListFlightPlanListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnListFlightPlanListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface OnListFlightPlanListener {
        fun onDisplayFlightPlanDetail()
    }

    companion object {
        private const val ARG_FLIGHT_PLAN = "command"

        fun newInstance(command: FlightRequestCommand): FlightPlanFragment {
            val args = Bundle().apply {
                putParcelable(ARG_FLIGHT_PLAN, command)
            }
            return FlightPlanFragment().apply { arguments = args }
        }
    }
}
