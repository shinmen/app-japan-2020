package fr.jbouffard.japan2020.View.PlanHoliday

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import fr.jbouffard.japan2020.Domain.Travel.Entity.Holiday
import fr.jbouffard.japan2020.Infrastructure.Utils.SnackBarStyler
import fr.jbouffard.japan2020.R
import fr.jbouffard.japan2020.View.PlanHoliday.DetailPlan.PlannedDayFragment
import kotlinx.android.synthetic.main.activity_planned_holiday.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PlannedHolidayActivity : AppCompatActivity(), PlannedDayFragment.OnHolidaySaveListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planned_holiday)
        val holiday = intent.getParcelableExtra<Holiday>(PlannedHolidayActivity.ARG_HOLIDAY)

        supportFragmentManager.beginTransaction()
                .setTransition(android.R.transition.explode)
                .add(android.R.id.content, PlannedDayFragment.newInstance(holiday), PlannedDayFragment.TAG)
                .commit()
    }

    override fun retry(tryAgain: suspend () -> Unit) {
            try {
                SnackBarStyler(this@PlannedHolidayActivity).errorSnackWithRetry(detailPlanContainer, "Impossible de mettre à jour les stats") {
                            GlobalScope.launch(Dispatchers.Main) {
                                tryAgain()
                            }
                        }
            } catch (e: Exception) {
                retry(tryAgain)
            }
    }

    companion object {
        const val ARG_HOLIDAY = "planned_holiday_arg"

        fun newIntent(packageContext: Context, holiday: Holiday) =
                Intent(packageContext, PlannedHolidayActivity::class.java).apply {
                    putExtra(ARG_HOLIDAY, holiday)
                }
    }
}
