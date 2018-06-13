package fr.jbouffard.japan2020.View.PlanHoliday


import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import fr.jbouffard.japan2020.R
import fr.jbouffard.japan2020.Infrastructure.DTO.Visit
import kotlinx.android.synthetic.main.fragment_visit_info_dialog.*
import org.jetbrains.anko.sdk25.coroutines.onClick


class VisitTourismInfoDialogFragment : DialogFragment() {

    private lateinit var mVisit: Visit
    private var mPosition: Int = 0
    lateinit var fragmentListener: onVisitPlaceChoice

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mVisit = it.getParcelable("visit")
            mPosition = it.getInt("position")
        }
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogTheme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_visit_info_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        city.text = mVisit.city
        //tourism_info.text = mVisit.tourismInfo
        visit_close.onClick {
            dismiss()
        }
        visit_confirm.onClick {
            fragmentListener.onPlaceChosen(mVisit, mPosition)
            dismiss()
        }
    }

    companion object {
        const val ARG_VISIT_INFO = "visit_tourism_info"
        fun newInstance(visit: Visit, position: Int): VisitTourismInfoDialogFragment {
            val args = Bundle().apply {
                putParcelable("visit", visit)
                putInt("position", position)
            }
            return VisitTourismInfoDialogFragment().apply { arguments = args }
        }
    }

    interface onVisitPlaceChoice {
        fun onPlaceChosen(visit: Visit, position: Int)
    }

}// Required empty public constructor
