package fr.jbouffard.japan2020.View.PlanHoliday.Visit

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import fr.jbouffard.japan2020.Infrastructure.DTO.Visit
import fr.jbouffard.japan2020.Infrastructure.Utils.inflate
import fr.jbouffard.japan2020.R
import kotlinx.android.synthetic.main.item_visit.view.*


class VisitRecyclerViewAdapter(private val visits: List<Visit>, private val listener: (Visit) -> Unit) : RecyclerView.Adapter<VisitRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent.inflate(R.layout.item_visit))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(visits[position], listener)

    override fun getItemCount() = visits.size


    class ViewHolder(visitView: View) : RecyclerView.ViewHolder(visitView) {
        fun bind(visit: Visit, listener: (Visit) -> Unit) = with(itemView) {
            val id = resources.getIdentifier(
                    visit.city.toLowerCase().replace(" ", ""),
                    "drawable", context.packageName
            )
            Picasso.get()
                    .load(id)
                    .fit()
                    .into(city_thumbnail)
            city.text = visit.city
            setOnClickListener { listener(visit) }
        }
    }
}
