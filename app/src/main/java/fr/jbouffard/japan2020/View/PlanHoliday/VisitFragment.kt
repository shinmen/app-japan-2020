package fr.jbouffard.japan2020.View.PlanHoliday

import android.content.Context
import android.os.Bundle
import android.support.transition.TransitionManager
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.stepstone.stepper.Step
import com.stepstone.stepper.VerificationError
import fr.jbouffard.japan2020.Domain.Travel.Entity.Holiday
import fr.jbouffard.japan2020.Presenter.OvernightRequestPresenter
import fr.jbouffard.japan2020.Presenter.VisitRequestPresenter

import fr.jbouffard.japan2020.R
import fr.jbouffard.japan2020.View.PlanFlight.FlightPlanFragment
import kotlinx.android.synthetic.main.fragment_overnight.view.*
import kotlinx.android.synthetic.main.fragment_visit.view.*
import kotlinx.android.synthetic.main.fragment_visit_list.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.toast
import org.koin.android.ext.android.inject

class VisitFragment
    : Fragment(), Step
{
    private var mListener: OnListFragmentInteractionListener? = null
    private val mPresenter: VisitRequestPresenter by inject()

    override fun onSelected() {
    }

    override fun verifyStep(): VerificationError? {
        return null
    }

    override fun onError(error: VerificationError) {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_visit_list, container, false)

        launch(UI) {
            try {
                TransitionManager.beginDelayedTransition(container!!)
                val visits = mPresenter.requestVisits()
                list.apply {
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    adapter = VisitRecyclerViewAdapter(visits) { visit ->
                        val dialog = VisitTourismInfoDialogFragment.newInstance(visit)
                        dialog.show(fragmentManager, VisitTourismInfoDialogFragment.ARG_VISIT_INFO)
                    }
                }
            } catch (e: Exception) {
                toast(e.message.toString())
            }
        }

        return view
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction()
    }

    companion object {
        const val VISIT_ARG = "holiday_for_visit"
        fun newInstance(holiday: Holiday): VisitFragment {
            val args = Bundle().apply {
                putParcelable(VISIT_ARG, holiday)
            }
            return VisitFragment().apply { arguments = args }
        }
    }
}
