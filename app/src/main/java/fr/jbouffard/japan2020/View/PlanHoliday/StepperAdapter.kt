package fr.jbouffard.japan2020.View.PlanHoliday

import android.content.Context
import android.support.v4.app.FragmentManager
import com.stepstone.stepper.Step
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter
import com.stepstone.stepper.viewmodel.StepViewModel
import fr.jbouffard.japan2020.Domain.Travel.Entity.Holiday
import fr.jbouffard.japan2020.R

/**
 * Created by julienb on 21/05/18.
 */
class StepperAdapter(fm: FragmentManager, context: Context, private val holiday: Holiday) : AbstractFragmentStepAdapter(fm, context) {
    override fun getCount(): Int {
        return holiday.holidayDuration.toInt()
    }

    override fun createStep(position: Int): Step {
        return DayFragment.newInstance(holiday, position+1)
    }

    override fun getViewModel(position: Int): StepViewModel {
        val builder = StepViewModel.Builder(context).apply {
            setNextButtonLabel(context.resources.getString(R.string.day_nb, position+2))
            setBackButtonLabel(context.getString(R.string.flights))
            //setBackButtonLabel(context.resources.getString(R.string.day_nb, position))
        }

        return builder.create()
    }
}