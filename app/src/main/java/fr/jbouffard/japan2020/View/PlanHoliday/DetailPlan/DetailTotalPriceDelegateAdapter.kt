package fr.jbouffard.japan2020.View.PlanHoliday.DetailPlan

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import fr.jbouffard.japan2020.Infrastructure.Utils.inflate
import fr.jbouffard.japan2020.R
import fr.jbouffard.japan2020.View.PlanHoliday.DetailDayRecyclerViewAdapter
import fr.jbouffard.japan2020.View.PlanHoliday.ViewType
import kotlinx.android.synthetic.main.item_detail_total_price_step.view.*
import kotlin.math.roundToInt

class DetailTotalPriceDelegateAdapter: DetailDayRecyclerViewAdapter.ViewTypeDelegateAdapter {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = DetailTotalPriceViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, detailDayLine: ViewType) = with(holder as DetailTotalPriceViewHolder) {
        bind(detailDayLine as DetailTotalPrice)
    }

    class DetailTotalPriceViewHolder(parent: ViewGroup): RecyclerView.ViewHolder(parent.inflate(R.layout.item_detail_total_price_step)) {
        fun bind(totalPrice: DetailTotalPrice) = with(itemView) {
            total.text = resources.getString(R.string.price, totalPrice.total.roundToInt().toString())
        }
    }
}