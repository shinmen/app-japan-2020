package fr.jbouffard.japan2020.View.PlanHoliday

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.transition.*
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ProgressBar
import fr.jbouffard.japan2020.Domain.Travel.Command.FlightRequestCommand
import fr.jbouffard.japan2020.Presenter.FlightRequestPresenter
import fr.jbouffard.japan2020.R
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import fr.jbouffard.japan2020.Infrastructure.DTO.FlightOffer
import kotlinx.android.synthetic.main.detail_going_flight_plan.*
import kotlinx.android.synthetic.main.detail_return_flight_plan.*
import kotlinx.android.synthetic.main.fragment_start_holiday_planning.*
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
        mPresenter = FlightRequestPresenter()

        launch(UI) {
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

                            mListener?.onStartHolidayPlanning()
                        }
                    }
                }
            } catch (e: Exception) {
                toast("Désolé, il y a une couille dans le potage")
            }
        }

        return view
    }
    
    private fun hydrateDetailGoingFlight(flightOffer: FlightOffer)
    {
        going_first_flight_origin_code.text = flightOffer.goingFlight.flights.first().departureAirport.code
        going_first_flight_departure_city.text = resources.getString(R.string.highlighted_time, flightOffer.goingFlight.flights.first().departureAirport.city, flightOffer.goingFlight.flights.first().departureAirport.country)
        going_first_flight_departure_date.text = highlightDate(flightOffer.goingFlight.flights.first().departureDate.toString("HH:mm dd MMM"))
        going_first_flight_destination_code.text = flightOffer.goingFlight.flights.first().arrivalAirport.code
        going_first_flight_arrival_city.text = resources.getString(R.string.highlighted_time, flightOffer.goingFlight.flights.first().arrivalAirport.city, flightOffer.goingFlight.flights.first().arrivalAirport.country)
        going_first_flight_arrival_date.text = highlightDate(flightOffer.goingFlight.flights.first().arrivalDate.toString("HH:mm dd MMM"))

        going_second_flight_departure_code.text = flightOffer.goingFlight.flights[1].departureAirport.code
        going_second_flight_departure_city.text = resources.getString(R.string.highlighted_time, flightOffer.goingFlight.flights[1].departureAirport.city, flightOffer.goingFlight.flights[1].departureAirport.country)
        going_second_flight_departure_date.text = highlightDate(flightOffer.goingFlight.flights[1].departureDate.toString("HH:mm dd MMM"))
        going_second_flight_arrival_code.text = flightOffer.goingFlight.flights[1].arrivalAirport.code
        going_second_flight_arrival_city.text = resources.getString(R.string.highlighted_time, flightOffer.goingFlight.flights[1].arrivalAirport.city, flightOffer.goingFlight.flights[1].arrivalAirport.country)
        going_second_flight_arrival_date.text = highlightDate(flightOffer.goingFlight.flights[1].arrivalDate.toString("HH:mm dd MMM"))

        if (flightOffer.goingFlight.flights.size > 2) {
            detail_going_third_flight_container.visibility = View.VISIBLE
            going_third_flight_departure_code.text = flightOffer.goingFlight.flights[2].departureAirport.code
            going_third_flight_departure_city.text = resources.getString(R.string.highlighted_time, flightOffer.goingFlight.flights[2].departureAirport.city, flightOffer.goingFlight.flights[2].departureAirport.country)
            going_third_flight_departure_date.text = highlightDate(flightOffer.goingFlight.flights[2].departureDate.toString("HH:mm dd MMM"))
            going_third_flight_arrival_code.text = flightOffer.goingFlight.flights[2].arrivalAirport.code
            going_third_flight_arrival_city.text = resources.getString(R.string.highlighted_time, flightOffer.goingFlight.flights[2].arrivalAirport.city, flightOffer.goingFlight.flights[2].arrivalAirport.country)
            going_third_flight_arrival_date.text = highlightDate(flightOffer.goingFlight.flights[2].arrivalDate.toString("HH:mm dd MMM"))
        }
    }

    private fun hydrateDetailReturnFlight(flightOffer: FlightOffer)
    {
        return_first_flight_origin_code.text = flightOffer.returnFlight.flights.first().departureAirport.code
        return_first_flight_departure_city.text = resources.getString(R.string.highlighted_time, flightOffer.returnFlight.flights.first().departureAirport.city, flightOffer.returnFlight.flights.first().departureAirport.country)
        return_first_flight_departure_date.text = highlightDate(flightOffer.returnFlight.flights.first().departureDate.toString("HH:mm dd MMM"))
        return_first_flight_destination_code.text = flightOffer.returnFlight.flights.first().arrivalAirport.code
        return_first_flight_arrival_city.text = resources.getString(R.string.highlighted_time, flightOffer.returnFlight.flights.first().arrivalAirport.city, flightOffer.returnFlight.flights.first().arrivalAirport.country)
        return_first_flight_arrival_date.text = highlightDate(flightOffer.returnFlight.flights.first().arrivalDate.toString("HH:mm dd MMM"))

        return_second_flight_departure_code.text = flightOffer.returnFlight.flights[1].departureAirport.code
        return_second_flight_departure_city.text = resources.getString(R.string.highlighted_time, flightOffer.returnFlight.flights[1].departureAirport.city, flightOffer.returnFlight.flights[1].departureAirport.country)
        return_second_flight_departure_date.text = highlightDate(flightOffer.returnFlight.flights[1].departureDate.toString("HH:mm dd MMM"))
        return_second_flight_arrival_code.text = flightOffer.returnFlight.flights[1].arrivalAirport.code
        return_second_flight_arrival_city.text = resources.getString(R.string.highlighted_time, flightOffer.returnFlight.flights[1].arrivalAirport.city, flightOffer.returnFlight.flights[1].arrivalAirport.country)
        return_second_flight_arrival_date.text = highlightDate(flightOffer.returnFlight.flights[1].arrivalDate.toString("HH:mm dd MMM"))

        if (flightOffer.returnFlight.flights.size > 2) {
            detail_return_third_flight_container.visibility = View.VISIBLE
            return_third_flight_departure_code.text = flightOffer.returnFlight.flights[2].departureAirport.code
            return_third_flight_departure_city.text = resources.getString(R.string.highlighted_time, flightOffer.returnFlight.flights[2].departureAirport.city, flightOffer.returnFlight.flights[2].departureAirport.country)
            return_third_flight_departure_date.text = highlightDate(flightOffer.returnFlight.flights[2].departureDate.toString("HH:mm dd MMM"))
            return_third_flight_arrival_code.text = flightOffer.returnFlight.flights[2].arrivalAirport.code
            return_third_flight_arrival_city.text = resources.getString(R.string.highlighted_time, flightOffer.returnFlight.flights[2].arrivalAirport.city, flightOffer.returnFlight.flights[2].arrivalAirport.country)
            return_third_flight_arrival_date.text = highlightDate(flightOffer.returnFlight.flights[2].arrivalDate.toString("HH:mm dd MMM"))
        }
    }

    private fun highlightDate(date :String): SpannableString {
        val highlighted = SpannableString(date)
        highlighted.setSpan(StyleSpan(Typeface.BOLD), 0, 5, 0)
        highlighted.setSpan(ForegroundColorSpan(Color.LTGRAY), 6, date.length, 0)
        highlighted.setSpan(RelativeSizeSpan(0.8f), 6, date.length, 0)

        return highlighted
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
