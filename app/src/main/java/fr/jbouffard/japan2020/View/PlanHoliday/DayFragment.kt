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
import fr.jbouffard.japan2020.Domain.DomainException
import fr.jbouffard.japan2020.Domain.Travel.Entity.Holiday
import fr.jbouffard.japan2020.Domain.Travel.ValueObject.City
import fr.jbouffard.japan2020.Infrastructure.DTO.OvernightOffer
import fr.jbouffard.japan2020.Infrastructure.DTO.Visit
import fr.jbouffard.japan2020.Presenter.DayRequestPresenter
import fr.jbouffard.japan2020.R
import fr.jbouffard.japan2020.View.PlanFlight.FlightRequestActivity
import kotlinx.android.synthetic.main.fragment_day_list.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import org.koin.android.ext.android.inject

class DayFragment
    : Fragment(), BlockingStep, VisitTourismInfoDialogFragment.OnVisitPlaceChoice, OvernightDetailDialogFragment.OnOvernightPlaceChoice
{
    private var mListener: OnVisitSchedulerListener? = null
    private lateinit var mHoliday: Holiday
    private var mDayNumber: Int = 1
    private val mPresenter: DayRequestPresenter by inject()

    override fun onOvernightPlaceChosen(overnight: OvernightOffer) {
        try {
            mPresenter.sleepIn(mHoliday, overnight)
            mListener?.onSleptIn(overnight)
            onDayEnded()
        } catch (e: DomainException) {
            mListener?.onError(e.message.toString())
        }
    }

    override fun onVisitPlaceChosen(visit: Visit) {
        mListener?.onVisited(visit)
        try {
            mPresenter.visitPlace(mHoliday, visit.city)
        } catch (e: DomainException) {
            mListener?.onError(e.message.toString())
        }
    }

    override fun onOvernightCityChosen(city: City) {
        launch(UI) {
            mListener?.onLoading()
            list_overnights.apply {
                val overnightOffers = mPresenter.requestOvernightsOffers(mHoliday.currentDate!!, city)
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)
                isNestedScrollingEnabled = true
                adapter = OvernightRecyclerViewAdapter(overnightOffers) { overnight ->
                    val dialog = OvernightDetailDialogFragment.newInstance(overnight)
                    dialog.fragmentListener = this@DayFragment
                    dialog.show(fragmentManager, OvernightDetailDialogFragment.ARG_OVERNIGHT_DETAIL)
                }
            }
            onNightStarted()
        }
    }

    override fun onSelected() {
    }

    override fun verifyStep(): VerificationError? {

        return null
    }

    override fun onError(error: VerificationError) {
        mListener?.onError(error.errorMessage)
    }

    override fun onBackClicked(callback: StepperLayout.OnBackClickedCallback?) {
        val i = FlightRequestActivity.newIntent(activity!!)
        startActivity(i)
    }

    override fun onNextClicked(callback: StepperLayout.OnNextClickedCallback?) {
        launch(UI) {
            try {
                mPresenter.finishDay(mHoliday)
                mListener?.onNextDay()
                callback?.goToNextStep()
            } catch (e: DomainException) {
                mListener?.onError(e.message.toString())
            }

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
                onLoading()
                val visits = mPresenter.requestVisits(mHoliday)
                list_visits.apply {
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    setHasFixedSize(true)
                    isNestedScrollingEnabled = true
                    adapter = VisitRecyclerViewAdapter(visits) { visit ->
                        val dialog = VisitTourismInfoDialogFragment.newInstance(visit)
                        dialog.fragmentListener = this@DayFragment
                        dialog.show(fragmentManager, VisitTourismInfoDialogFragment.ARG_VISIT_INFO)
                    }
                }
                onListVisitsLoaded()
            } catch (e: Exception) {
                mListener?.onError(e.message.toString())
            }
        }

        return view
    }

    private fun onListVisitsLoaded() {
        loading_day.visibility = View.GONE
        list_visits.visibility = View.VISIBLE
    }

    private fun onLoading() {
        loading_day.visibility = View.VISIBLE
        list_visits.visibility = View.GONE
        list_overnights.visibility = View.GONE
    }

    private fun onNightStarted() {
        loading_day.visibility = View.GONE
        list_visits.visibility = View.GONE
        list_overnights.visibility = View.VISIBLE
    }

    private fun onDayEnded() {
        loading_day.visibility = View.GONE
        list_visits.visibility = View.GONE
        list_overnights.visibility = View.GONE
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnVisitSchedulerListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnVisitSchedulerListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface OnVisitSchedulerListener {
        fun onVisited(visit: Visit)
        fun onSleptIn(overnight: OvernightOffer)
        fun onNextDay()
        fun onLoading()
        fun onError(error: String)
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
