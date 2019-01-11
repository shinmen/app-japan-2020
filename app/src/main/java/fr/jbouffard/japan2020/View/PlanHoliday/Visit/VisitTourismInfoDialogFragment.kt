package fr.jbouffard.japan2020.View.PlanHoliday.Visit


import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fr.jbouffard.japan2020.Domain.Travel.ValueObject.City
import fr.jbouffard.japan2020.Infrastructure.DTO.Visit
import fr.jbouffard.japan2020.R
import kotlinx.android.synthetic.main.fragment_visit_info_dialog.*
import org.jetbrains.anko.sdk27.coroutines.onClick


class VisitTourismInfoDialogFragment : DialogFragment() {

    private lateinit var mVisit: Visit
    lateinit var fragmentListener: OnVisitPlaceChoice

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mVisit = it.getParcelable("visit")
        }
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogTheme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_visit_info_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        city.text = mVisit.city
        area.text = getString(R.string.area,mVisit.area)
        nearby_cities.text = getString(R.string.nearby_cities,mVisit.nearbyCities.joinToString(", "))
        tourism_info.text = mVisit.tourismInfo
        visit_close.onClick {
            dismiss()
        }
        stay_tonight.onClick {
            fragmentListener.onOvernightCityChosen(City(mVisit.city))
            dismiss()
        }
        visit_confirm.onClick {
            fragmentListener.onVisitPlaceChosen(mVisit)
            dismiss()
        }
    }

    companion object {
        const val ARG_VISIT_INFO = "visit_tourism_info"
        fun newInstance(visit: Visit): VisitTourismInfoDialogFragment {
            val args = Bundle().apply {
                putParcelable("visit", visit)
            }
            return VisitTourismInfoDialogFragment().apply { arguments = args }
        }
    }

    interface OnVisitPlaceChoice {
        fun onVisitPlaceChosen(visit: Visit)
        fun onOvernightCityChosen(city: City)
    }

}// Required empty public constructor
