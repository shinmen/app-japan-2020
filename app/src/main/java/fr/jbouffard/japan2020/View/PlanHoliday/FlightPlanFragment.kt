package fr.jbouffard.japan2020.View.PlanHoliday

import android.content.Context
import android.os.Bundle
import android.support.transition.*
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ProgressBar
import fr.jbouffard.japan2020.Domain.Travel.Command.FlightRequestCommand
import fr.jbouffard.japan2020.Presenter.FlightRequestPresenter
import fr.jbouffard.japan2020.R
import kotlinx.android.synthetic.main.fragment_start_holiday_planning.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import android.widget.Button
import fr.jbouffard.japan2020.Infrastructure.DTO.FlightOffer
import kotlinx.android.synthetic.main.detail_going_first_flight.*
import kotlinx.android.synthetic.main.detail_going_second_flight.*
import kotlinx.android.synthetic.main.detail_going_third_flight.*
import kotlinx.android.synthetic.main.detail_return_first_flight.*
import kotlinx.android.synthetic.main.detail_return_second_flight.*
import kotlinx.android.synthetic.main.detail_return_third_flight.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.toast


class FlightPlanFragment : Fragment() {
    private var mFlightRequestCommand: FlightRequestCommand? = null
    private var mListener: OnStartHolidayPlanningFlightPlanListener? = null
    lateinit var mPresenter: FlightRequestPresenter

    private lateinit var mDetailView: FrameLayout

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
            mDetailView = view.findViewById(R.id.start_holiday_container)


            TransitionManager.beginDelayedTransition(container!!)
            loading.visibility = View.GONE
            list.visibility = View.VISIBLE
            list.apply {
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
                        mListener?.onStartHolidayPlanning()
                    }
                }
            }
        }




        return view
    }
    
    private fun hydrateDetailGoingFlight(flightOffer: FlightOffer)
    {
        going_first_flight_origin_code.text = flightOffer.goingFlight.flights.first().departureAirport
        going_first_flight_departure_date.text = flightOffer.goingFlight.flights.first().departureDate.toString("dd MMM")
        going_first_flight_departure_time.text = flightOffer.goingFlight.flights.first().departureDate.toString("HH:mm")
        going_first_flight_destination_code.text = flightOffer.goingFlight.flights.first().arrivalAirport
        going_first_flight_arrival_date.text = flightOffer.goingFlight.flights.first().arrivalDate.toString("dd MMM")
        going_first_flight_arrival_time.text = flightOffer.goingFlight.flights.first().arrivalDate.toString("HH:mm")

        going_second_flight_origin_code.text = flightOffer.goingFlight.flights[1].departureAirport
        going_second_flight_departure_date.text = flightOffer.goingFlight.flights[1].departureDate.toString("dd MMM")
        going_second_flight_departure_time.text = flightOffer.goingFlight.flights[1].departureDate.toString("HH:mm")
        going_second_flight_destination_code.text = flightOffer.goingFlight.flights[1].arrivalAirport
        going_second_flight_arrival_date.text = flightOffer.goingFlight.flights[1].arrivalDate.toString("dd MMM")
        going_second_flight_arrival_time.text = flightOffer.goingFlight.flights[1].arrivalDate.toString("HH:mm")

        if (flightOffer.goingFlight.flights.size > 2) {
            going_third_flight_content.visibility = View.VISIBLE
            going_third_flight_origin_code.text = flightOffer.goingFlight.flights[2].departureAirport
            going_third_flight_departure_date.text = flightOffer.goingFlight.flights[2].departureDate.toString("dd MMM")
            going_third_flight_departure_time.text = flightOffer.goingFlight.flights[2].departureDate.toString("HH:mm")
            going_third_flight_destination_code.text = flightOffer.goingFlight.flights[2].arrivalAirport
            going_third_flight_arrival_date.text = flightOffer.goingFlight.flights[2].arrivalDate.toString("dd MMM")
            going_third_flight_arrival_time.text = flightOffer.goingFlight.flights[2].arrivalDate.toString("HH:mm")
        }
    }

    private fun hydrateDetailReturnFlight(flightOffer: FlightOffer)
    {
        return_first_flight_origin_code.text = flightOffer.returnFlight.flights.first().departureAirport
        return_first_flight_departure_date.text = flightOffer.returnFlight.flights.first().departureDate.toString("dd MMM")
        return_first_flight_departure_time.text = flightOffer.returnFlight.flights.first().departureDate.toString("HH:mm")
        return_first_flight_destination_code.text = flightOffer.returnFlight.flights.first().arrivalAirport
        return_first_flight_arrival_date.text = flightOffer.returnFlight.flights.first().arrivalDate.toString("dd MMM")
        return_first_flight_arrival_time.text = flightOffer.returnFlight.flights.first().arrivalDate.toString("HH:mm")

        return_second_flight_origin_code.text = flightOffer.returnFlight.flights[1].departureAirport
        return_second_flight_departure_date.text = flightOffer.returnFlight.flights[1].departureDate.toString("dd MMM")
        return_second_flight_departure_time.text = flightOffer.returnFlight.flights[1].departureDate.toString("HH:mm")
        return_second_flight_destination_code.text = flightOffer.returnFlight.flights[1].arrivalAirport
        return_second_flight_arrival_date.text = flightOffer.returnFlight.flights[1].arrivalDate.toString("dd MMM")
        return_second_flight_arrival_time.text = flightOffer.returnFlight.flights[1].arrivalDate.toString("HH:mm")

        if (flightOffer.returnFlight.flights.size > 2) {
            return_third_flight_content.visibility = View.VISIBLE
            return_third_flight_origin_code.text = flightOffer.returnFlight.flights[2].departureAirport
            return_third_flight_departure_date.text = flightOffer.returnFlight.flights[2].departureDate.toString("dd MMM")
            return_third_flight_departure_time.text = flightOffer.returnFlight.flights[2].departureDate.toString("HH:mm")
            return_third_flight_destination_code.text = flightOffer.returnFlight.flights[2].arrivalAirport
            return_third_flight_arrival_date.text = flightOffer.returnFlight.flights[2].arrivalDate.toString("dd MMM")
            return_third_flight_arrival_time.text = flightOffer.returnFlight.flights[2].arrivalDate.toString("HH:mm")
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
        fun onStartHolidayPlanning()
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
