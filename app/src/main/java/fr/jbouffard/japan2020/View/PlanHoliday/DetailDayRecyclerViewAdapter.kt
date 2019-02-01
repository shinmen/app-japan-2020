package fr.jbouffard.japan2020.View.PlanHoliday

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

class DetailDayRecyclerViewAdapter(private val detailLines: List<ViewType>, private val delegateAdapters: SparseArrayCompat<ViewTypeDelegateAdapter>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = delegateAdapters[viewType]!!.onCreateViewHolder(parent)

    override fun getItemCount() = detailLines.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int)  = delegateAdapters.get(getItemViewType(position))!!.onBindViewHolder(holder, detailLines[position])

    override fun getItemViewType(position: Int): Int = detailLines[position].getViewType()

    interface ViewTypeDelegateAdapter {
        fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder
        fun onBindViewHolder(holder: RecyclerView.ViewHolder, detailDayLine: ViewType)
    }
}