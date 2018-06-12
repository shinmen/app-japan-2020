package fr.jbouffard.japan2020.View.PlanFlight

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import fr.jbouffard.japan2020.Domain.Travel.ValueObject.City
import fr.jbouffard.japan2020.Infrastructure.Command.FlightRequestCommand
import fr.jbouffard.japan2020.Infrastructure.DTO.FlightOffer
import fr.jbouffard.japan2020.Presenter.FlightRequestPresenter
import fr.jbouffard.japan2020.R
import fr.jbouffard.japan2020.View.PlanHoliday.PlanningActivity
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import org.koin.android.ext.android.inject

class FlightRequestActivity
        : AppCompatActivity(), FlightRequestFragment.OnFlightRequestListener, FlightPlanFragment.OnStartHolidayPlanningFlightPlanListener
{
    private val mPresenter: FlightRequestPresenter by inject()

    override fun onFlightPlanSelected(command: FlightRequestCommand) {
        val fragment = FlightPlanFragment.newInstance(command)
        supportFragmentManager
                .beginTransaction()
                .setTransition(android.R.transition.explode)
                .replace(android.R.id.content, fragment, "flightplan")
                .addToBackStack("flightplan")
                .commit()
    }

    override fun onStartHolidayPlanning(flightOffer: FlightOffer) {
        launch(UI) {
            val holiday = mPresenter.selectRoundTrip(flightOffer)
            val arrivalCity = City(
                    flightOffer.goingFlight.flights.last().arrivalAirport.city,
                    flightOffer.goingFlight.flights.last().arrivalAirport.country
            )
            val i = PlanningActivity.newIntent(this@FlightRequestActivity, holiday, arrivalCity)

            startActivity(i)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flight_request)

        val flightPlanFragment = supportFragmentManager.findFragmentByTag("flightplan") as FlightPlanFragment?
        if (flightPlanFragment == null) {
            val fragment = FlightRequestFragment.newInstance()
            supportFragmentManager
                    .beginTransaction()
                    .setTransition(android.R.transition.explode)
                    .add(android.R.id.content, fragment)
                    .commit()
        }
    }

    companion object {
        fun newIntent(packageContext: Context): Intent {
            return Intent(packageContext, FlightRequestActivity::class.java)
        }
    }
}
