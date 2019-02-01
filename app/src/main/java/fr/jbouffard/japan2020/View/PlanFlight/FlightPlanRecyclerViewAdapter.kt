package fr.jbouffard.japan2020.View.PlanFlight

import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StyleSpan
import android.view.View
import android.view.ViewGroup
import fr.jbouffard.japan2020.Infrastructure.DTO.FlightOffer
import fr.jbouffard.japan2020.R
import kotlinx.android.synthetic.main.item_flightplan.view.*
import android.text.style.RelativeSizeSpan
import fr.jbouffard.japan2020.Infrastructure.Utils.inflate


/**
 * Created by julienb on 27/03/18.
 */
class FlightPlanRecyclerViewAdapter(
        private val flights: List<FlightOffer>, private val listener: (FlightOffer) -> Unit
    ) : RecyclerView.Adapter<FlightPlanRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent.inflate(R.layout.item_flightplan))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(flights[position], listener)

    override fun getItemCount() = flights.size

    class ViewHolder(flightPlanView: View) : RecyclerView.ViewHolder(flightPlanView) {
        fun bind(flightOffer: FlightOffer, listener: (FlightOffer) -> Unit) = with(itemView) {
            departure_date.text = highlightHour(flightOffer.goingFlight.flights.first().departureDate.toString("d MMM à H:mm"))
//            going_duration.text = resources.getString(R.string.duration, "%.2f".format(flightOffer.goingFlight.duration))
            going_duration.text = resources.getString(R.string.duration, formatDuration(flightOffer.goingFlight.duration))

            return_date.text = highlightHour(flightOffer.returnFlight.flights.last().departureDate.toString("d MMM à H:mm"))
            //return_duration.text = resources.getString(R.string.duration, "%.2f".format(flightOffer.returnFlight.duration))
            return_duration.text = resources.getString(R.string.duration, formatDuration(flightOffer.returnFlight.duration))
            total_fare.text = resources.getString(R.string.euro_price, "%.2f".format(flightOffer.totalRatePerAdult))
            setOnClickListener { listener(flightOffer) }
        }

        private fun highlightHour(date: String): SpannableString {
            val styledText = SpannableString(date)
            val flag = Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            styledText.setSpan(StyleSpan(Typeface.BOLD), date.length - 5, date.length, flag)
            styledText.setSpan(RelativeSizeSpan(1.3f), date.length - 5, date.length, flag)

            return styledText
        }

        private fun formatDuration(duration: Float): String {
            return "%.2f".format(duration).replace(",", "h")
        }
    }
}

