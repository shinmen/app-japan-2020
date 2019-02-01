package fr.jbouffard.japan2020.View.PlanHoliday.Budget

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import fr.jbouffard.japan2020.Infrastructure.LocalPersistence.Entity.Budget
import fr.jbouffard.japan2020.Infrastructure.Utils.inflate
import fr.jbouffard.japan2020.R
import fr.jbouffard.japan2020.View.PlanHoliday.DetailDayRecyclerViewAdapter
import fr.jbouffard.japan2020.View.PlanHoliday.ViewType
import kotlinx.android.synthetic.main.item_budget_line.view.*
import kotlin.math.roundToInt

class BudgetDelegateAdapter: DetailDayRecyclerViewAdapter.ViewTypeDelegateAdapter {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = BudgetViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, detailDayLine: ViewType) {
        holder as BudgetViewHolder
        holder.bind(detailDayLine as Budget)
    }

    class BudgetViewHolder(parent: ViewGroup):RecyclerView.ViewHolder(parent.inflate(R.layout.item_budget_line)) {
        fun bind(budgetLine: Budget) = with(itemView) {
            label.text = budgetLine.serviceLabel
            price.text = resources.getString(R.string.price, budgetLine.rate.roundToInt())
        }
    }
}