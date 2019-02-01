package fr.jbouffard.japan2020.View.PlanHoliday.DetailPlan

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import fr.jbouffard.japan2020.Infrastructure.Utils.inflate
import fr.jbouffard.japan2020.R
import fr.jbouffard.japan2020.View.PlanHoliday.DetailDayRecyclerViewAdapter
import fr.jbouffard.japan2020.View.PlanHoliday.ViewType
import kotlinx.android.synthetic.main.item_detail_railpass_step.view.*
import org.joda.time.DateTime
import kotlin.math.round

class DetailRailpassDelegateAdapter: DetailDayRecyclerViewAdapter.ViewTypeDelegateAdapter {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = DetailRailpassViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, detailDayLine: ViewType) = with(holder as DetailRailpassViewHolder) {
        bind(detailDayLine as DetailRailpassPackage)
    }

    class DetailRailpassViewHolder(parent: ViewGroup): RecyclerView.ViewHolder(parent.inflate(R.layout.item_detail_railpass_step)) {
        fun bind(railpass: DetailRailpassPackage) = with(itemView) {
            railpass_package_name.text = railpass.railpassPackage.packageName
            price.text = resources.getString(R.string.price, round(railpass.railpassPackage.price).toString())
            railpass_period.text = resources.getString(
                    R.string.railpass_period,
                    DateTime(railpass.railpassPackage.startDate).toString("d MMM"),
                    DateTime(railpass.railpassPackage.endDate).toString("d MMM")
            )
        }
    }
}