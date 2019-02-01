package fr.jbouffard.japan2020.View.PlanHoliday

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import fr.jbouffard.japan2020.Domain.Travel.Entity.Holiday
import fr.jbouffard.japan2020.R
import fr.jbouffard.japan2020.View.PlanHoliday.DetailPlan.PlannedDayFragment

class PlannedHolidayActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planned_holiday)
        val holiday = intent.getParcelableExtra<Holiday>(PlannedHolidayActivity.ARG_HOLIDAY)

        supportFragmentManager.beginTransaction()
                .setTransition(android.R.transition.explode)
                .add(android.R.id.content, PlannedDayFragment.newInstance(holiday), PlannedDayFragment.TAG)
                .commit()
    }

    companion object {
        const val ARG_HOLIDAY = "planned_holiday_arg"

        fun newIntent(packageContext: Context, holiday: Holiday) =
                Intent(packageContext, PlannedHolidayActivity::class.java).apply {
                    putExtra(ARG_HOLIDAY, holiday)
                }
    }
}
