package fr.jbouffard.japan2020.View.PlanHoliday

import android.content.Context
import android.support.v4.app.FragmentManager
import com.stepstone.stepper.Step
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter
import com.stepstone.stepper.viewmodel.StepViewModel
import fr.jbouffard.japan2020.Domain.Travel.Entity.Day
import fr.jbouffard.japan2020.Domain.Travel.Entity.Holiday

/**
 * Created by julienb on 21/05/18.
 */
class StepperAdapter(fm: FragmentManager, context: Context, private val holiday: Holiday) : AbstractFragmentStepAdapter(fm, context) {
    private val days by lazy {   0.rangeTo(holiday.holidayDuration).toList() }
    override fun getCount(): Int {
        return (holiday.holidayDuration * 2).toInt()
    }

    override fun createStep(position: Int): Step {
        days[position]
        return if (position %2 == 0) {
            VisitFragment.newInstance(holiday, position)
        } else {
            OvernightFragment.newInstance(holiday)
        }
    }

    override fun getViewModel(position: Int): StepViewModel {
        val builder = StepViewModel.Builder(context)
        /*val dayCount = count / 2
        when {
            position < 1 -> builder.setNextButtonLabel("Jour 1")
            position == 2 -> builder.setNextButtonLabel("Jour 2")
            position %2 == 0 -> builder.setNextButtonLabel("Jour ${position-1}")
            else -> builder.setNextButtonLabel("Jour ${position-1}")
        }*/
        if (position %2 == 0) {
            builder.setNextButtonLabel("Jour ${position+1}")
        } else {
            builder.setNextButtonLabel("Jour $position")
        }

        return builder.create()
    }
}