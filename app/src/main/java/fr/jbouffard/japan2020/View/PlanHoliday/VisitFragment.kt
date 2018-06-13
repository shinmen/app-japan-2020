package fr.jbouffard.japan2020.View.PlanHoliday

import android.content.Context
import android.os.Bundle
import android.support.transition.TransitionManager
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.stepstone.stepper.BlockingStep
import com.stepstone.stepper.StepperLayout
import com.stepstone.stepper.VerificationError
import fr.jbouffard.japan2020.Domain.Travel.Entity.Holiday
import fr.jbouffard.japan2020.Infrastructure.DTO.Visit
import fr.jbouffard.japan2020.Presenter.VisitRequestPresenter

import fr.jbouffard.japan2020.R
import kotlinx.android.synthetic.main.fragment_visit_list.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import org.jetbrains.anko.support.v4.toast
import org.koin.android.ext.android.inject

class VisitFragment
    : Fragment(), BlockingStep, VisitTourismInfoDialogFragment.onVisitPlaceChoice
{
    private var mListener: OnVisitSchedulerListener? = null
    private lateinit var mHoliday: Holiday
    private var mDayNumber: Int = 1
    private val mPresenter: VisitRequestPresenter by inject()

    override fun onPlaceChosen(visit: Visit) {
        mListener!!.onVisited(visit)
        launch {
            mPresenter.visitPlace(mHoliday, visit.city, mDayNumber)
        }
    }

    override fun onSelected() {
    }

    override fun verifyStep(): VerificationError? {
        return null
    }

    override fun onError(error: VerificationError) {
    }

    override fun onBackClicked(callback: StepperLayout.OnBackClickedCallback?) {
        callback?.goToPrevStep()
    }

    override fun onNextClicked(callback: StepperLayout.OnNextClickedCallback?) {
        callback?.goToNextStep()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mHoliday = it.getParcelable(VISIT_ARG)
            mDayNumber = it.getInt("dayNumber")
        }
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
                        dialog.fragmentListener = this@VisitFragment
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
        if (context is OnVisitSchedulerListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface OnVisitSchedulerListener {
        fun onVisited(visit: Visit)
    }

    companion object {
        const val VISIT_ARG = "holiday_for_visit"
        fun newInstance(holiday: Holiday, dayNumber: Int): VisitFragment {
            val args = Bundle().apply {
                putParcelable(VISIT_ARG, holiday)
                putInt("dayNumber", dayNumber)

            }
            return VisitFragment().apply { arguments = args }
        }
    }
}
