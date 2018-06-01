package fr.jbouffard.japan2020.View.PlanHoliday

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import fr.jbouffard.japan2020.Infrastructure.DTO.OvernightOffer
import fr.jbouffard.japan2020.R
import kotlinx.android.synthetic.main.fragment_overnight.view.*

class OvernightRecyclerViewAdapter(private val overnights: List<OvernightOffer>, private val listener: (OvernightOffer) -> Unit) : RecyclerView.Adapter<OvernightRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = OvernightRecyclerViewAdapter.ViewHolder(parent.inflate(R.layout.fragment_overnight))

    override fun onBindViewHolder(holder: OvernightRecyclerViewAdapter.ViewHolder, position: Int) = holder.bind(overnights[position], listener)

    override fun getItemCount() = overnights.size


    class ViewHolder(overnightView: View) : RecyclerView.ViewHolder(overnightView) {
        fun bind(overnightOffer: OvernightOffer, listener: (OvernightOffer) -> Unit) = with(itemView) {
            Picasso.get().load(overnightOffer.thumbnailUrl)
                    //.resize(100, 100)
                    //.centerCrop()
                    .into(thumbnail)
            pricePerPax.text = "${overnightOffer.pricePerPax}€/pers."
            name.text = overnightOffer.accommodation.commercialName
            bed.text = overnightOffer.accommodation.bedNb.toString()
            setOnClickListener { listener(overnightOffer) }
        }
    }

    private fun ViewGroup.inflate(layoutRes: Int): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, false)
    }
}


