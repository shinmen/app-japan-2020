package fr.jbouffard.japan2020.View.PlanHoliday

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import fr.jbouffard.japan2020.Infrastructure.LocalPersistence.Entity.Budget
import fr.jbouffard.japan2020.Infrastructure.Utils.inflate
import fr.jbouffard.japan2020.R
import kotlinx.android.synthetic.main.item_budget_line.view.*

class BudgetDelegateAdapter: BudgetRecyclerViewAdapter.ViewTypeDelegateAdapter {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = BudgetViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, budgetLine: ViewType) {
        holder as BudgetViewHolder
        holder.bind(budgetLine as Budget)
    }

    class BudgetViewHolder(parent: ViewGroup):RecyclerView.ViewHolder(parent.inflate(R.layout.item_budget_line)) {
        fun bind(budgetLine: Budget) = with(itemView) {
            label.text = budgetLine.serviceLabel
            price.text = budgetLine.rate.toString()
        }
    }
}