package fr.jbouffard.japan2020.View.PlanHoliday

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fr.jbouffard.japan2020.Infrastructure.DTO.FlightOffer
import fr.jbouffard.japan2020.R
import kotlinx.android.synthetic.main.fragment_flightplan.view.*

/**
 * Created by julienb on 27/03/18.
 */
class FlightPlanRecyclerViewAdapter(
        private val flights: List<FlightOffer>, private val listener: (FlightOffer) -> Unit
    ) : RecyclerView.Adapter<FlightPlanRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent.inflate(R.layout.fragment_flightplan))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(flights[position], listener)

    override fun getItemCount() = flights.size

    class ViewHolder(flightPlanView: View) : RecyclerView.ViewHolder(flightPlanView) {
        fun bind(flightOffer: FlightOffer, listener: (FlightOffer) -> Unit) = with(itemView) {
            departure_date.text = flightOffer.goingFlight.flights.first().departureDate.toString("d MMM à H:mm")
            going_duration.text = resources.getString(R.string.duration, "%.2f".format(flightOffer.goingFlight.duration))
            return_date.text = flightOffer.returnFlight.flights.last().departureDate.toString("d MMM à H:mm")
            return_duration.text = resources.getString(R.string.duration, "%.2f".format(flightOffer.returnFlight.duration))
            total_fare.text = resources.getString(R.string.euro_price, "%.2f".format(flightOffer.totalRatePerAdult))
            setOnClickListener { listener(flightOffer) }
        }
    }

    private fun ViewGroup.inflate(layoutRes: Int): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, false)
    }
}

