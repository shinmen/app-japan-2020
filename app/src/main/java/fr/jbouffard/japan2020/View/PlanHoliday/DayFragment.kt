package fr.jbouffard.japan2020.View.PlanHoliday

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
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
import fr.jbouffard.japan2020.Presenter.VisitRequestPresenter
import fr.jbouffard.japan2020.R
import fr.jbouffard.japan2020.View.PlanFlight.FlightRequestActivity
import fr.jbouffard.japan2020.View.PlanHoliday.Budget.*
import fr.jbouffard.japan2020.View.PlanHoliday.Overnight.OvernightFragment
import fr.jbouffard.japan2020.View.PlanHoliday.Visit.VisitFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import org.koin.android.ext.android.inject

class DayFragment
    : Fragment(),
        BlockingStep,
        VisitFragment.OnVisitListener,
        OvernightFragment.OnOvernightListener,
        BudgetFragment.OnBudgetListener
{

    private var mListener: OnDaySchedulerListener? = null
    private lateinit var mHoliday: Holiday
    private var mDayNumber: Int = 1
    private val mPresenter: VisitRequestPresenter by inject()

    override fun onSleptIn(overnight: OvernightOffer) {
        mListener?.onSleptIn(overnight)
    }

    override fun onOvernightCityChosen(city: City) {
        childFragmentManager
                .beginTransaction()
                .setTransition(android.R.transition.explode)
                .replace(R.id.day_container, OvernightFragment.newInstance(mHoliday, city), VisitFragment.TAG)
                .commit()
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
        GlobalScope.launch(Dispatchers.Main) {
            try {
                mPresenter.finishDay(mHoliday)
                mListener?.onNextDay(mHoliday.currentDate)
                callback?.goToNextStep()
            } catch (e: DomainException) {
                mListener?.onError(e.message.toString())
            }
        }
    }

    override fun onCompleteClicked(callback: StepperLayout.OnCompleteClickedCallback?) {
        val i = PlannedHolidayActivity.newIntent(activity!!, mHoliday)
        startActivity(i)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mHoliday = it.getParcelable(HOLIDAY_ARG)!!
            mDayNumber = it.getInt(DAY_NB_ARG)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_day, container, false)

        childFragmentManager
                .beginTransaction()
                .setTransition(android.R.transition.explode)
                .add(R.id.day_container, VisitFragment.newInstance(mHoliday), VisitFragment.TAG)
                .commit()

        return view
    }

    override fun onDayEnded() {
        childFragmentManager
                .beginTransaction()
                .setTransition(android.R.transition.explode)
                .replace(R.id.day_container, BudgetFragment.newInstance(mHoliday), BudgetFragment.TAG)
                .commit()
    }

    override fun onError(error: String) {
        mListener?.onError(error)
    }

    override fun onVisited(visit: Visit) {
        mListener?.onVisited(visit)
    }

    override fun onLoad() {
        //loading_day.visibility = View.VISIBLE
        //day_container.visibility = View.GONE
    }

    override fun onLoaded() {
        //loading_day.visibility = View.GONE
        //day_container.visibility = View.VISIBLE
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnDaySchedulerListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnDaySchedulerListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface OnDaySchedulerListener {
        fun onVisited(visit: Visit)
        fun onSleptIn(overnight: OvernightOffer)
        fun onNextDay(currentDate: DateTime?)
        fun onLoading()
        fun onError(error: String)
    }

    companion object {
        const val HOLIDAY_ARG = "holiday_for_day"
        const val DAY_NB_ARG = "day_number"

        fun newInstance(holiday: Holiday, dayNumber: Int): DayFragment {
            val args = Bundle().apply {
                putParcelable(HOLIDAY_ARG, holiday)
                putInt(DAY_NB_ARG, dayNumber)
            }
            return DayFragment().apply { arguments = args }
        }
    }
}
