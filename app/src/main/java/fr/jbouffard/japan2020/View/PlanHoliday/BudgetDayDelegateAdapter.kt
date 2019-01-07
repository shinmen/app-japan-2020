package fr.jbouffard.japan2020.View.PlanHoliday

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import fr.jbouffard.japan2020.Infrastructure.Utils.inflate
import fr.jbouffard.japan2020.R
import kotlinx.android.synthetic.main.item_budget_day_line.view.*

class BudgetDayDelegateAdapter: BudgetRecyclerViewAdapter.ViewTypeDelegateAdapter {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = BudgetDayViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, budgetLine: ViewType) {
        holder as BudgetDayViewHolder
        holder.bind(budgetLine as BudgetDay)
    }

    class BudgetDayViewHolder(parent: ViewGroup): RecyclerView.ViewHolder(parent.inflate(R.layout.item_budget_day_line)) {
        fun bind(budgetLine: BudgetDay) = with(itemView) {
            day.text = budgetLine.getDayNb().toString("dd/MM")
        }
    }
}