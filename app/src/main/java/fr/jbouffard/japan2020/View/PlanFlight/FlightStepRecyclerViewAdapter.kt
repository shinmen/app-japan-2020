package fr.jbouffard.japan2020.View.PlanFlight

import android.graphics.Color
import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.view.View
import android.view.ViewGroup
import fr.jbouffard.japan2020.Infrastructure.DTO.FlightInfo
import fr.jbouffard.japan2020.Infrastructure.Utils.inflate
import fr.jbouffard.japan2020.R
import kotlinx.android.synthetic.main.item_flight_step.view.*

class FlightStepRecyclerViewAdapter(private val flights: List<FlightInfo>) : RecyclerView.Adapter<FlightStepRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent.inflate(R.layout.item_flight_step))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(flights[position], position, itemCount)

    override fun getItemCount() = flights.size

    class ViewHolder(flightPlanView: View) : RecyclerView.ViewHolder(flightPlanView) {
        fun bind(flightOffer: FlightInfo, position: Int, size: Int) = with(itemView) {
                flight_origin_code.text = flightOffer.departureAirport.code
                flight_departure_date.text = highlightDate(flightOffer.departureDate.toString("HH:mm dd MMM"))
                flight_departure_city.text = resources.getString(R.string.highlighted_time, flightOffer.departureAirport.city, flightOffer.departureAirport.country)
                flight_destination_code.text = flightOffer.arrivalAirport.code
                flight_arrival_date.text = highlightDate(flightOffer.arrivalDate.toString("HH:mm dd MMM"))
                flight_arrival_city.text = resources.getString(R.string.highlighted_time, flightOffer.arrivalAirport.city, flightOffer.arrivalAirport.country)
                when (position) {
                    0 -> top_timeline.visibility = View.GONE
                    size - 1 -> bottom_timeline.visibility = View.GONE
                }
        }

        private fun highlightDate(date :String): SpannableString {
            val highlighted = SpannableString(date)
            highlighted.setSpan(StyleSpan(Typeface.BOLD), 0, 5, 0)
            highlighted.setSpan(ForegroundColorSpan(Color.LTGRAY), 6, date.length, 0)
            highlighted.setSpan(RelativeSizeSpan(0.8f), 6, date.length, 0)

            return highlighted
        }
    }
}
