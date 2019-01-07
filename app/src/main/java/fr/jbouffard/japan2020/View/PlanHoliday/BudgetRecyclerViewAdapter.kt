package fr.jbouffard.japan2020.View.PlanHoliday

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

class BudgetRecyclerViewAdapter(private val budgetLines: List<ViewType>, private val delegateAdapters: SparseArrayCompat<ViewTypeDelegateAdapter>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = delegateAdapters[viewType]!!.onCreateViewHolder(parent)

    override fun getItemCount() = budgetLines.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int)  = delegateAdapters.get(getItemViewType(position))!!.onBindViewHolder(holder, budgetLines[position])

    override fun getItemViewType(position: Int): Int = budgetLines[position].getViewType()

    interface ViewTypeDelegateAdapter {
        fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder
        fun onBindViewHolder(holder: RecyclerView.ViewHolder, budgetLine: ViewType)
    }
}