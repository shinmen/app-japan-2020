package fr.jbouffard.japan2020.View.PlanHoliday.DetailPlan

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import fr.jbouffard.japan2020.Infrastructure.Utils.inflate
import fr.jbouffard.japan2020.R
import fr.jbouffard.japan2020.View.PlanHoliday.DetailDayRecyclerViewAdapter
import fr.jbouffard.japan2020.View.PlanHoliday.ViewType
import kotlinx.android.synthetic.main.item_detail_overnight_step.view.*
import kotlin.math.round

class OvernightDelegateAdapter: DetailDayRecyclerViewAdapter.ViewTypeDelegateAdapter {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = FlightPlanViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, detailDayLine: ViewType) = with(holder as FlightPlanViewHolder) {
        bind(detailDayLine as OvernightDaySchedule)
    }

    class FlightPlanViewHolder(parent: ViewGroup): RecyclerView.ViewHolder(parent.inflate(R.layout.item_detail_overnight_step)) {
        fun bind(overnightLine: OvernightDaySchedule) = with(itemView) {
            city.text = overnightLine.overnight.address.commercialCityName
            price.text = resources.getString(R.string.price, round(overnightLine.overnight.rate).toString())
            Picasso.get()
                    .load(overnightLine.overnight.picture)
                    .fit()
                    .into(thumbnail)
        }
    }
}