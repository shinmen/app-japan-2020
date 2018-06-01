package fr.jbouffard.japan2020.View.PlanHoliday

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import fr.jbouffard.japan2020.R
import android.support.v4.view.ViewPager
import com.mapbox.mapboxsdk.Mapbox
import com.stepstone.stepper.StepperLayout
import com.mapbox.mapboxsdk.maps.SupportMapFragment
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.constants.Style
import com.mapbox.mapboxsdk.constants.Style.SATELLITE
import com.mapbox.mapboxsdk.maps.MapboxMapOptions
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback


class PlanningActivity
    : AppCompatActivity(),
        VisitFragment.OnListFragmentInteractionListener,
        OvernightFragment.OnListFragmentInteractionListener
{
    override fun onListFragmentInteraction() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planning)

        val indicator = findViewById<StepperLayout>(R.id.indicator)
        val adapter = StepperAdapter(supportFragmentManager, this, 6)
        indicator.setAdapter(adapter)
        //Mapbox.getInstance(this, getString(R.string.map_box_api_key))

        // Create supportMapFragment
        val mapFragment: SupportMapFragment
  /*      if (savedInstanceState == null) {

            // Create fragment
            val transaction = supportFragmentManager.beginTransaction()

            val tokyo = LatLng(35.723199, 139.734506)

            // Build mapboxMap
            val options = MapboxMapOptions().apply {
                styleUrl(Style.DARK)
                camera(CameraPosition.Builder().target(tokyo).zoom(5.toDouble()).build())
                maxZoomPreference(8.toDouble())
                minZoomPreference(5.toDouble())
            }
            // Create map fragment
            mapFragment = SupportMapFragment.newInstance(options)

            // Add map fragment to parent container
            transaction.add(R.id.map_container, mapFragment, "com.mapbox.map")
            transaction.commit()
        } else {
            mapFragment = supportFragmentManager.findFragmentByTag("com.mapbox.map") as SupportMapFragment
        }

        mapFragment.getMapAsync({

        })*/
    }

    companion object {
        fun newIntent(packageContext: Context): Intent {
            return Intent(packageContext, PlanningActivity::class.java)
        }
    }
}
