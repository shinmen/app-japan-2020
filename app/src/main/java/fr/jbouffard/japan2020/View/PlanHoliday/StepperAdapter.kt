package fr.jbouffard.japan2020.View.PlanHoliday

import android.content.Context
import android.support.v4.app.FragmentManager
import com.stepstone.stepper.Step
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter

/**
 * Created by julienb on 21/05/18.
 */
class StepperAdapter(fm: FragmentManager, context: Context) : AbstractFragmentStepAdapter(fm, context) {
    override fun getCount(): Int {
        return 5
    }

    override fun createStep(position: Int): Step {
        return if (position %2 == 0) {
            VisitFragment.newInstance()
        } else {
            OvernightFragment.newInstance()
        }
    }
}