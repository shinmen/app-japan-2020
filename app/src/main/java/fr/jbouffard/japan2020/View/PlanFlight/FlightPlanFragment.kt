package fr.jbouffard.japan2020.View.PlanFlight

import android.content.Context
import android.os.Bundle
import android.support.constraint.Group
import android.support.transition.TransitionManager
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import fr.jbouffard.japan2020.Infrastructure.Command.FlightRequestCommand
import fr.jbouffard.japan2020.Infrastructure.DTO.FlightOffer
import fr.jbouffard.japan2020.presenter.FlightRequestPresenter
import fr.jbouffard.japan2020.R
import kotlinx.android.synthetic.main.recycler_flight_info_list.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.longToast
import org.jetbrains.anko.support.v4.toast
import org.koin.android.ext.android.inject
import java.io.IOException

class FlightPlanFragment : Fragment() {
    private var mFlightRequestCommand: FlightRequestCommand? = null
    private var mListener: OnStartHolidayPlanningFlightPlanListener? = null
    private val mPresenter: FlightRequestPresenter by inject()

    private lateinit var mDetailView: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mFlightRequestCommand = it.getParcelable(ARG_FLIGHT_PLAN)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_flightplan, container, false)
        val loading = view.findViewById<Group>(R.id.group_loading_flight)

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val offers = mPresenter.requestFlightPrice(mFlightRequestCommand!!)
                val list = view.findViewById<RecyclerView>(R.id.flight_plan_list)
                mDetailView = view.findViewById(R.id.start_holiday_container)

                TransitionManager.beginDelayedTransition(container!!)
                loading.visibility = View.GONE
                list.apply {
                    visibility = View.VISIBLE
                    layoutManager = GridLayoutManager(activity, 2)
                    adapter = FlightPlanRecyclerViewAdapter(offers) { flightOffer ->
                        if (mDetailView.visibility == View.GONE) {
                            TransitionManager.beginDelayedTransition(container)
                            mDetailView.visibility = View.VISIBLE
                        }
                        airline_company_name.text = resources.getString(R.string.company_name, flightOffer.goingFlight.companyName)
                        hydrateDetailGoingFlight(flightOffer)
                        hydrateDetailReturnFlight(flightOffer)
                        hide_detail_btn.onClick {
                            TransitionManager.beginDelayedTransition(container)
                            mDetailView.visibility = View.GONE
                        }

                        start_planning_btn.onClick {
                            try {
                                mListener?.onStartHolidayPlanning(flightOffer)
                            }  catch (e: Exception) {
                                longToast(e.message.toString())
                            }
                        }
                    }
                }
            } catch (e: IOException) {
                toast(resources.getString(R.string.flight_offers_error))
            }
        }

        return view
    }
    
    private fun hydrateDetailGoingFlight(flightOffer: FlightOffer)
    {
        going_flight_list.apply {
            visibility = View.VISIBLE
            layoutManager = LinearLayoutManager(activity)
            adapter = FlightStepRecyclerViewAdapter(isGoing = true, flights = flightOffer.goingFlight.flights)
        }
    }

    private fun hydrateDetailReturnFlight(flightOffer: FlightOffer) {
        return_flight_list.apply {
            visibility = View.VISIBLE
            layoutManager = LinearLayoutManager(activity)
            adapter = FlightStepRecyclerViewAdapter(isGoing = false, flights = flightOffer.returnFlight.flights)
        }
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
        fun onStartHolidayPlanning(flightOffer: FlightOffer)
    }

    companion object {
        const val TAG = "flightplan"
        private const val ARG_FLIGHT_PLAN = "command"

        fun newInstance(command: FlightRequestCommand): FlightPlanFragment {
            val args = Bundle().apply {
                putParcelable(ARG_FLIGHT_PLAN, command)
            }
            return FlightPlanFragment().apply { arguments = args }
        }
    }
}
