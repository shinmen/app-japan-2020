package fr.jbouffard.japan2020.View.PlanHoliday

import android.content.Context
import android.content.Intent
import android.graphics.drawable.VectorDrawable
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
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
import fr.jbouffard.japan2020.Infrastructure.DTO.OvernightOffer
import fr.jbouffard.japan2020.Infrastructure.DTO.Visit
import fr.jbouffard.japan2020.Infrastructure.Utils.SnackBarStyler
import fr.jbouffard.japan2020.Infrastructure.Utils.VectorDrawableTransformer
import fr.jbouffard.japan2020.R
import kotlinx.android.synthetic.main.activity_planning.*


class PlanningActivity
    : AppCompatActivity(),
        DayFragment.OnVisitSchedulerListener
{
    private var mMap: MapboxMap? = null
    private var markerList: MutableList<LatLng> = mutableListOf()
    private var mDayNumber: Int = 1
    private var mFinishedDay: Boolean = false

    override fun onError(error: String) {
        SnackBarStyler(this).errorSnack(planning_container, error)
    }

    override fun onVisited(visit: Visit) {
        val icon = VectorDrawableTransformer.toBitmap(getDrawable(R.drawable.ic_visit_icon) as VectorDrawable)
        val marker = mMap?.markers
                ?.filter {
                    it.position == visit.geolocation
                }?.takeIf { it.isNotEmpty() }
                ?.get(0)

        if(marker == null) {
            mMap?.addMarker(MarkerOptions().apply {
                position(visit.geolocation)
                icon(IconFactory.getInstance(this@PlanningActivity).fromBitmap(icon))
                title(visit.city)
                snippet(getString(R.string.day_nb, mDayNumber))
            })
            markerList.add(visit.geolocation)
        } else {
            marker.snippet = "${marker.snippet}, ${getString(R.string.day_nb, mDayNumber)}"
        }
        mMap?.addPolyline(PolylineOptions()
                        .addAll(markerList.asIterable())
                        .color(ContextCompat.getColor(this, R.color.colorPrimaryDark))
                        .width(2.toFloat()))
        SnackBarStyler(this).infoSnack(planning_container, getString(R.string.added_visit))
    }

    override fun onSleptIn(overnight: OvernightOffer) {
        val iconBitmap = VectorDrawableTransformer.toBitmap(getDrawable(R.drawable.ic_accomodation_icon) as VectorDrawable)
        val icon = IconFactory.getInstance(this@PlanningActivity).fromBitmap(iconBitmap)

        mMap?.addMarker(MarkerOptions().apply {
            position(overnight.geolocation)
            icon(icon)
            title(overnight.accommodation.city)
            snippet(getString(R.string.day_nb, mDayNumber))
        })
        markerList.add(overnight.geolocation)
        mMap?.addPolyline(PolylineOptions()
                .addAll(markerList.asIterable())
                .color(ContextCompat.getColor(this, R.color.colorPrimaryDark))
                .width(2.toFloat()))
        SnackBarStyler(this).infoSnack(planning_container, getString(R.string.stay_over_added, overnight.accommodation.city))
        mFinishedDay = true
    }

    override fun onNextDay() {
        step_day.text = getString(R.string.day_nb, ++mDayNumber)
    }

    override fun onLoading() {
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putBoolean(FINISHED_DAY, mFinishedDay)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planning)

        step_day.text = resources.getString(R.string.day_nb, 1)
        val arrivalCity = intent.getParcelableExtra<City>(ARRIVAL_CITY_ARG)
        val arrivalGeolocation = GeolocationForArrivalCity.geolocation(arrivalCity)

        val holiday = intent.getParcelableExtra<Holiday>(HOLIDAY_ARG)
        holiday.startHolidayPlanning()
        val indicator = findViewById<StepperLayout>(R.id.indicator)
        val adapter = StepperAdapter(supportFragmentManager, this, holiday)
        indicator.setAdapter(adapter)

        initMap(savedInstanceState, arrivalGeolocation, arrivalCity)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        mFinishedDay = savedInstanceState?.getBoolean(FINISHED_DAY)!!
    }

    override fun onResume() {
        super.onResume()
        if(mFinishedDay) {
            //indicator.visibility = View.GONE
        }
    }

    private fun initMap(savedInstanceState: Bundle?, arrivalGeolocation: LatLng, arrivalCity: City) {
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
            val icon = VectorDrawableTransformer.toBitmap(getDrawable(R.drawable.ic_visit_icon) as VectorDrawable)
            map.addMarker(MarkerOptions().apply {
                position(arrivalGeolocation)
                icon(IconFactory.getInstance(this@PlanningActivity).fromBitmap(icon))
                title(arrivalCity.name)
                snippet(getString(R.string.day_nb, 1))
            }
            )
            markerList.add(arrivalGeolocation)
            map.setLatLngBoundsForCameraTarget(JAPAN_BOUNDS)
        })
    }

    companion object {
        private const val HOLIDAY_ARG = "holiday_arg"
        private const val ARRIVAL_CITY_ARG = "arrival_city_arg"
        private const val FINISHED_DAY = "finished_day"
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
