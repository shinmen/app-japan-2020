package fr.jbouffard.japan2020.View.PlanHoliday

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentManager
import fr.jbouffard.japan2020.R

class FlightRequestActivity
    : AppCompatActivity(), FlightRequestFragment.OnFragmentInteractionListener
{
    override fun onFragmentInteraction() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flight_request)

        supportFragmentManager
                .beginTransaction()
                .setTransition(android.R.transition.explode)
                .add(android.R.id.content, FlightRequestFragment.newInstance())
                .commit()
    }

    companion object {
        fun newIntent(packageContext: Context): Intent {
            return Intent(packageContext, FlightRequestActivity::class.java)
        }
    }
}
