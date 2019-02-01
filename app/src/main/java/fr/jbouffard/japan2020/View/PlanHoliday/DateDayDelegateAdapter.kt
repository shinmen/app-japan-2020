package fr.jbouffard.japan2020.View.PlanHoliday

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import fr.jbouffard.japan2020.Infrastructure.Utils.inflate
import fr.jbouffard.japan2020.R
import kotlinx.android.synthetic.main.item_date_detail_day_line.view.*

class DateDayDelegateAdapter: DetailDayRecyclerViewAdapter.ViewTypeDelegateAdapter {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = BudgetDayViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, detailDayLine: ViewType) {
        holder as BudgetDayViewHolder
        holder.bind(detailDayLine as DateDetailDay)
    }

    class BudgetDayViewHolder(parent: ViewGroup): RecyclerView.ViewHolder(parent.inflate(R.layout.item_date_detail_day_line)) {
        fun bind(dateDetailLine: DateDetailDay) = with(itemView) {
            day.text = dateDetailLine.getDayNb().toString("dd/MM")
        }
    }
}