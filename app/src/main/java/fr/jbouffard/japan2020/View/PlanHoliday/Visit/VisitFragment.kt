package fr.jbouffard.japan2020.View.PlanHoliday.Visit

import android.os.Bundle
import android.support.transition.TransitionManager
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fr.jbouffard.japan2020.Domain.DomainException
import fr.jbouffard.japan2020.Domain.Travel.Entity.Holiday
import fr.jbouffard.japan2020.Domain.Travel.ValueObject.City
import fr.jbouffard.japan2020.Infrastructure.DTO.Visit
import fr.jbouffard.japan2020.Presenter.VisitRequestPresenter
import fr.jbouffard.japan2020.R
import kotlinx.android.synthetic.main.recycler_visit_list.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class VisitFragment : Fragment(),
        VisitTourismInfoDialogFragment.OnVisitPlaceChoice {

    private lateinit var mHoliday: Holiday
    private val mPresenter: VisitRequestPresenter by inject()

    private var mListener: OnVisitListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            mHoliday = it.getParcelable(ARG_HOLIDAY)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.recycler_visit_list, container, false)
        if (parentFragment is OnVisitListener) {
            mListener = parentFragment as OnVisitListener
        }
        GlobalScope.launch(Dispatchers.Main) {
            try {
                mListener?.onLoad()
                TransitionManager.beginDelayedTransition(container!!)
                val visits = mPresenter.requestVisits(mHoliday)
                list.apply {
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    setHasFixedSize(true)
                    isNestedScrollingEnabled = true
                    adapter = VisitRecyclerViewAdapter(visits) { visit ->
                        val dialog = VisitTourismInfoDialogFragment.newInstance(visit)
                        dialog.fragmentListener = this@VisitFragment
                        dialog.show(fragmentManager, VisitTourismInfoDialogFragment.ARG_VISIT_INFO)
                    }
                }
                mListener?.onLoaded()
            } catch (e: Exception) {
                mListener?.onError(e.message.toString())
            }
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mListener = null
    }

    override fun onVisitPlaceChosen(visit: Visit) {
        try {
            mPresenter.visitPlace(mHoliday, visit.city)
            mListener?.onVisited(visit)
        } catch (e: DomainException) {
            mListener?.onError(e.message.toString())
        }
    }

    override fun onOvernightCityChosen(city: City) {
        mListener?.onOvernightCityChosen(city)
    }

    interface OnVisitListener {
        fun onError(error: String)
        fun onLoad()
        fun onLoaded()
        fun onVisited(visit: Visit)
        fun onOvernightCityChosen(city: City)
    }

    companion object {
        const val TAG = "visitFragment"
        const val ARG_HOLIDAY = "visit_holiday"

        @JvmStatic
        fun newInstance(holiday: Holiday) =
            VisitFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_HOLIDAY, holiday)
                }
            }
    }
}
