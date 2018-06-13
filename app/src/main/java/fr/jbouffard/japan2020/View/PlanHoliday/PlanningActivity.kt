package fr.jbouffard.japan2020.View.PlanHoliday

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.app.AppCompatActivity
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.annotations.IconFactory
import com.mapbox.mapboxsdk.annotations.MarkerOptions
import com.mapbox.mapboxsdk.annotations.PolylineOptions
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.constants.Style
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.geometry.LatLngBounds
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.MapboxMapOptions
import com.mapbox.mapboxsdk.maps.SupportMapFragment
import com.stepstone.stepper.StepperLayout
import fr.jbouffard.japan2020.Domain.Travel.Entity.Holiday
import fr.jbouffard.japan2020.Domain.Travel.ValueObject.City
import fr.jbouffard.japan2020.Domain.Utils.GeolocationForArrivalCity
import fr.jbouffard.japan2020.Infrastructure.DTO.Visit
import fr.jbouffard.japan2020.R
import org.jetbrains.anko.toast


class PlanningActivity
    : AppCompatActivity(),
        VisitFragment.OnVisitSchedulerListener,
        OvernightFragment.OnListFragmentInteractionListener
{
    private var mMap: MapboxMap? = null
    private var markerList: MutableList<LatLng> = mutableListOf()

    override fun onVisited(visit: Visit) {
        mMap?.addMarker(MarkerOptions().apply {
            position(visit.geolocation)
            title(visit.city)
        })
        markerList.add(visit.geolocation)
        mMap?.addPolyline(PolylineOptions()
                        .addAll(markerList.asIterable())
                        .color(ContextCompat.getColor(this, R.color.colorPrimaryDark))
                        .width(2.toFloat()))
        toast(getString(R.string.added_visit))
    }

    override fun onListFragmentInteraction() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planning)
        val arrivalCity = intent.getParcelableExtra<City>(ARRIVAL_CITY_ARG)
        val arrivalGeolocation = GeolocationForArrivalCity.geolocation(arrivalCity)

        val holiday = intent.getParcelableExtra<Holiday>(HOLIDAY_ARG)
        holiday.startHolidayPlanning()
        val indicator = findViewById<StepperLayout>(R.id.indicator)
        val adapter = StepperAdapter(supportFragmentManager, this, holiday)
        indicator.setAdapter(adapter)
        Mapbox.getInstance(this, getString(R.string.map_box_api_key))

        // Create supportMapFragment
        val mapFragment: SupportMapFragment
        if (savedInstanceState == null) {
            // Create fragment
            val transaction = supportFragmentManager.beginTransaction()
            // Build mapboxMap
            val options = MapboxMapOptions().apply {
                styleUrl(Style.DARK)
                camera(CameraPosition.Builder().target(arrivalGeolocation).zoom(5.toDouble()).build())
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

        mapFragment.getMapAsync({ map ->
            mMap = map
            map.addMarker(MarkerOptions().apply {
                position(arrivalGeolocation)
                title(arrivalCity.name)
                }
            )
            markerList.add(arrivalGeolocation)
            map.setLatLngBoundsForCameraTarget(JAPAN_BOUNDS)
        })
    }

    companion object {
        private const val HOLIDAY_ARG = "holiday_arg"
        private const val ARRIVAL_CITY_ARG = "arrival_city_arg"
        fun newIntent(packageContext: Context, holiday: Holiday, arrivalCity: City): Intent {
            return Intent(packageContext, PlanningActivity::class.java).apply {
                putExtra(HOLIDAY_ARG, holiday)
                putExtra(ARRIVAL_CITY_ARG, arrivalCity)
            }
        }
        private val JAPAN_BOUNDS = LatLngBounds.Builder()
                .include(LatLng(43.956052, 147.860351))
                .include(LatLng(30.392789, 127.874031))
                .build()
    }
}
