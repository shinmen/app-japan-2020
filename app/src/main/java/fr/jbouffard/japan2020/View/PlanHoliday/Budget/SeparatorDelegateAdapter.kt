package fr.jbouffard.japan2020.View.PlanHoliday.Budget

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import fr.jbouffard.japan2020.Infrastructure.Utils.inflate
import fr.jbouffard.japan2020.R

class SeparatorDelegateAdapter: BudgetRecyclerViewAdapter.ViewTypeDelegateAdapter {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = SeparatorViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, budgetLine : ViewType) {

    }

    class SeparatorViewHolder(parent: ViewGroup): RecyclerView.ViewHolder(parent.inflate(R.layout.view_separator))
}
