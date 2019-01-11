package fr.jbouffard.japan2020.View.PlanHoliday.Overnight

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import fr.jbouffard.japan2020.Infrastructure.DTO.OvernightOffer
import fr.jbouffard.japan2020.Infrastructure.Utils.inflate
import fr.jbouffard.japan2020.R
import kotlinx.android.synthetic.main.item_overnight.view.*

class OvernightRecyclerViewAdapter(private val overnights: List<OvernightOffer>, private val listener: (OvernightOffer) -> Unit) : RecyclerView.Adapter<OvernightRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent.inflate(R.layout.item_overnight))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(overnights[position], listener)

    override fun getItemCount() = overnights.size


    class ViewHolder(overnightView: View) : RecyclerView.ViewHolder(overnightView) {
        fun bind(overnightOffer: OvernightOffer, listener: (OvernightOffer) -> Unit) = with(itemView) {
            Picasso.get().load(overnightOffer.thumbnailUrl)
                    .into(thumbnail)
            pricePerPax.text = resources.getString(R.string.price_per_pax, overnightOffer.pricePerPax)
            name.text = overnightOffer.accommodation.commercialName
            bed.text = overnightOffer.accommodation.bedNb.toString()
            setOnClickListener { listener(overnightOffer) }
        }
    }
}


