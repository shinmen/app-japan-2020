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
import fr.jbouffard.japan2020.Domain.Travel.ValueObject.City
import fr.jbouffard.japan2020.Infrastructure.DTO.Visit
import fr.jbouffard.japan2020.Presenter.VisitRequestPresenter

import fr.jbouffard.japan2020.R
import fr.jbouffard.japan2020.View.PlanFlight.FlightRequestActivity
import kotlinx.android.synthetic.main.fragment_day_list.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking
import org.jetbrains.anko.support.v4.longToast
import org.jetbrains.anko.support.v4.toast
import org.koin.android.ext.android.inject

class DayFragment
    : Fragment(), BlockingStep, VisitTourismInfoDialogFragment.onVisitPlaceChoice
{
    private var mListener: OnVisitSchedulerListener? = null
    private lateinit var mHoliday: Holiday
    private var mDayNumber: Int = 1
    private val mPresenter: VisitRequestPresenter by inject()

    override fun onVisitPlaceChosen(visit: Visit) {
        mListener?.onVisited(visit)
        launch {
            mPresenter.visitPlace(mHoliday, visit.city, mDayNumber)
        }
    }

    override fun onOvernightPlaceChosen(city: City) {

    }

    override fun onSelected() {
    }

    override fun verifyStep(): VerificationError? {
        onLoading()

        return null
    }

    override fun onError(error: VerificationError) {
        longToast(error.errorMessage)
    }

    override fun onBackClicked(callback: StepperLayout.OnBackClickedCallback?) {
        val i = FlightRequestActivity.newIntent(activity!!)
        startActivity(i)
    }

    override fun onNextClicked(callback: StepperLayout.OnNextClickedCallback?) {
        launch(UI) {
            mPresenter.finishDay(mHoliday)
            mListener?.onNextDay()
            callback?.goToNextStep()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mHoliday = it.getParcelable(VISIT_ARG)
            mDayNumber = it.getInt("dayNumber")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_day_list, container, false)

        launch(UI) {
            try {
                TransitionManager.beginDelayedTransition(container!!)
                val visits = mPresenter.requestVisits()
                list.apply {
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    setHasFixedSize(true)
                    isNestedScrollingEnabled = true
                    adapter = VisitRecyclerViewAdapter(visits) { visit ->
                        val dialog = VisitTourismInfoDialogFragment.newInstance(visit)
                        dialog.fragmentListener = this@DayFragment
                        dialog.show(fragmentManager, VisitTourismInfoDialogFragment.ARG_VISIT_INFO)
                    }
                }
                list_overnights.apply {
                    val offers = mPresenter.requestOvernightsOffers()
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    setHasFixedSize(true)
                    isNestedScrollingEnabled = true
                    adapter = OvernightRecyclerViewAdapter(offers) { overnight ->
                        val dialog = OvernightDetailDialogFragment.newInstance(overnight)
                        dialog.show(fragmentManager, OvernightDetailDialogFragment.ARG_OVERNIGHT_DETAIL)
                    }
                }
                onLoaded()
            } catch (e: Exception) {
                toast(e.message.toString())
            }
        }

        return view
    }

    private fun onLoaded() {
        loading_day.visibility = View.GONE
        list.visibility = View.VISIBLE
        list_overnights.visibility = View.VISIBLE
    }

    private fun onLoading() {
        loading_day.visibility = View.VISIBLE
        list.visibility = View.GONE
        list_overnights.visibility = View.GONE
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
        fun onNextDay()
        fun onLoading()
    }

    companion object {
        const val VISIT_ARG = "holiday_for_visit"
        fun newInstance(holiday: Holiday, dayNumber: Int): DayFragment {
            val args = Bundle().apply {
                putParcelable(VISIT_ARG, holiday)
                putInt("dayNumber", dayNumber)
            }
            return DayFragment().apply { arguments = args }
        }
    }
}
