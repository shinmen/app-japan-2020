package fr.jbouffard.japan2020.View.PlanHoliday

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import fr.jbouffard.japan2020.R

class FlightRequestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flight_request)
    }

    companion object {
        fun newIntent(packageContext: Context): Intent {
            return Intent(packageContext, FlightRequestActivity::class.java)
        }
    }
}
