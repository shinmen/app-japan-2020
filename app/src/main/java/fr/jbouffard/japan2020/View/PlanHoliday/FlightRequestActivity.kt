package fr.jbouffard.japan2020.View.PlanHoliday

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import fr.jbouffard.japan2020.Domain.Travel.Command.FlightRequestCommand
import fr.jbouffard.japan2020.Presenter.FlightRequestPresenter
import fr.jbouffard.japan2020.R
import org.jetbrains.anko.toast

class FlightRequestActivity
        : AppCompatActivity(),
        FlightRequestFragment.OnFlightRequestListener,
        FlightPlanFragment.OnListFlightPlanListener,
        StartHolidayPlanningFragment.OnStartHolidayPlanningFlightPlanListener
{
    private var mPresenter: FlightRequestPresenter? = null

    override fun onFlightPlanSelected(command: FlightRequestCommand) {
        val fragment = FlightPlanFragment.newInstance(command)
        fragment.mPresenter = mPresenter!!
        supportFragmentManager
                .beginTransaction()
                .setTransition(android.R.transition.explode)
                .replace(android.R.id.content, fragment, "flightplan")
                .addToBackStack("flightplan")
                .commit()
    }

    override fun onDisplayFlightPlanDetail() {
        val flightPlanFragment = supportFragmentManager.findFragmentByTag("flightplan")
        val startHolidayFragment = StartHolidayPlanningFragment.newInstance()
        supportFragmentManager
                .beginTransaction()
                .setTransition(android.R.transition.explode)
                .remove(flightPlanFragment)
                .add(android.R.id.content, startHolidayFragment, "startholiday")
                .add(android.R.id.content, flightPlanFragment, "flightplan")
                .addToBackStack(null)
                .commit()
    }

    override fun onStartHolidayPlanning() {
        toast("youhou")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flight_request)
        mPresenter = FlightRequestPresenter()

        val flightPlanFragment = supportFragmentManager.findFragmentByTag("flightplan") as FlightPlanFragment?
        flightPlanFragment?.let {
            it.mPresenter = mPresenter as FlightRequestPresenter
        }

        if (flightPlanFragment == null) {
            val fragment = FlightRequestFragment.newInstance()
            fragment.mPresenter = mPresenter!!

            supportFragmentManager
                    .beginTransaction()
                    .setTransition(android.R.transition.explode)
                    .add(android.R.id.content, fragment)
                    .commit()
        }
    }

    override fun onDestroy() {
        mPresenter = null
        super.onDestroy()
    }

    companion object {
        fun newIntent(packageContext: Context): Intent {
            return Intent(packageContext, FlightRequestActivity::class.java)
        }
    }
}
