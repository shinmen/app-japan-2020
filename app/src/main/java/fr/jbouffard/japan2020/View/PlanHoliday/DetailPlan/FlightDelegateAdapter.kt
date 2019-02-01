package fr.jbouffard.japan2020.View.PlanHoliday.DetailPlan

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import fr.jbouffard.japan2020.Infrastructure.Utils.inflate
import fr.jbouffard.japan2020.R
import fr.jbouffard.japan2020.View.PlanHoliday.ViewType
import fr.jbouffard.japan2020.View.PlanHoliday.DetailDayRecyclerViewAdapter
import kotlinx.android.synthetic.main.item_detail_flight_step.view.*
import kotlinx.android.synthetic.main.item_flightplan.view.*

class FlightDelegateAdapter: DetailDayRecyclerViewAdapter.ViewTypeDelegateAdapter {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = FlightPlanViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, detailDayLine: ViewType) = with(holder as FlightPlanViewHolder) {
        bind(detailDayLine as DetailFlightSchedule)
    }

    class FlightPlanViewHolder(parent: ViewGroup): RecyclerView.ViewHolder(parent.inflate(R.layout.item_detail_flight_step)) {
        fun bind(flightPlanLine: DetailFlightSchedule) = with(itemView) {
            company_name.text = flightPlanLine.company
            departure_time.text = flightPlanLine.flight.departureDate.toString("d MMM à H:mm")
            flight_number.text = resources.getString(R.string.flight_nb, flightPlanLine.flight.flightNumber)
            arrival_time.text = flightPlanLine.flight.arrivalDate.toString("d MMM à H:mm")
            duration.text = flightPlanLine.flight.duration.toString().replace(".", "h")
            departure_city.text = resources.getString(
                    R.string.highlighted_destination,
                    flightPlanLine.flight.departureCity.name,
                    flightPlanLine.flight.departureCity.country
            )
            arrival_city.text = resources.getString(
                    R.string.highlighted_destination,
                    flightPlanLine.flight.arrivalCity.name,
                    flightPlanLine.flight.arrivalCity.country
            )



        }
    }
}