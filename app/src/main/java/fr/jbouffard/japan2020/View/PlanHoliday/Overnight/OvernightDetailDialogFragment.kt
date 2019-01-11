package fr.jbouffard.japan2020.View.PlanHoliday.Overnight


import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Picasso
import fr.jbouffard.japan2020.Infrastructure.DTO.OvernightOffer
import fr.jbouffard.japan2020.R
import kotlinx.android.synthetic.main.fragment_overnight_detail_dialog.*
import org.jetbrains.anko.sdk27.coroutines.onClick


class OvernightDetailDialogFragment : DialogFragment() {

    private lateinit var mOvernight: OvernightOffer
    lateinit var fragmentListener: OnOvernightPlaceChoice

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mOvernight = it.getParcelable("overnight")
        }
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogTheme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_overnight_detail_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val listPictures = list_pictures
        mOvernight.otherThumbnails.forEach {
            val img = ImageView(activity)
            Picasso.get().load(it).into(img)
            listPictures.addView(img)
        }
        commercial_name.text = mOvernight.accommodation.commercialName
        neighborhood.text = mOvernight.accommodation.city
        property_type.text = mOvernight.accommodation.propertyType
        capacity.text = mOvernight.accommodation.capacity.toString()
        bedroom_nb.text = mOvernight.accommodation.bedroomsNb.toString()
        bed_nb.text = mOvernight.accommodation.bedNb.toString()
        bathroom_nb.text = mOvernight.accommodation.bathRoomNb.toString()

        close.onClick {
            dismiss()
        }

        confirm.onClick {
            fragmentListener.onOvernightPlaceChosen(mOvernight)
            dismiss()
        }
    }

    companion object {
        const val ARG_OVERNIGHT_DETAIL = "overnight_detail"
        fun newInstance(overnight: OvernightOffer): OvernightDetailDialogFragment {
            val args = Bundle().apply {
                putParcelable("overnight", overnight)
            }
            return OvernightDetailDialogFragment().apply { arguments = args }
        }
    }

    interface OnOvernightPlaceChoice {
        fun onOvernightPlaceChosen(overnight: OvernightOffer)
    }

}// Required empty public constructor
