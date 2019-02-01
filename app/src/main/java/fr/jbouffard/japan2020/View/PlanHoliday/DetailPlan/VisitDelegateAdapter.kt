package fr.jbouffard.japan2020.View.PlanHoliday.DetailPlan

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import fr.jbouffard.japan2020.Infrastructure.Utils.inflate
import fr.jbouffard.japan2020.R
import fr.jbouffard.japan2020.View.PlanHoliday.DetailDayRecyclerViewAdapter
import fr.jbouffard.japan2020.View.PlanHoliday.ViewType
import kotlinx.android.synthetic.main.item_detail_visit_step.view.*

class VisitDelegateAdapter: DetailDayRecyclerViewAdapter.ViewTypeDelegateAdapter {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = VisitViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, detailDayLine: ViewType) = with(holder as VisitViewHolder) {
        bind(detailDayLine as VisitDaySchedule)
    }

    class VisitViewHolder(parent: ViewGroup): RecyclerView.ViewHolder(parent.inflate(R.layout.item_detail_visit_step)) {
        fun bind(visitLine: VisitDaySchedule) = with(itemView) {
            visitLine
            origin_city.text = visitLine.visit.origin
            destination_city.text = visitLine.visit.destination
            val id = resources.getIdentifier(
                    visitLine.visit.destination.toLowerCase().replace(" ", ""),
                    "drawable", context.packageName
            )
            Picasso.get()
                    .load(id)
                    .fit()
                    .into(city_picture)
        }
    }
}