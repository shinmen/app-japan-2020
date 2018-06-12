package fr.jbouffard.japan2020.Domain.Utils

import com.mapbox.mapboxsdk.geometry.LatLng
import fr.jbouffard.japan2020.Domain.Travel.ValueObject.City

/**
 * Created by julienb on 12/06/18.
 */
class GeolocationForArrivalCity {
    companion object {
        fun geolocation(city: City) = when(city.name) {
            "Osaka" -> LatLng(34.676419, 135.498154)
            "Nagoya" -> LatLng(35.153861, 136.934372)
            else -> LatLng(35.742707, 139.731073) // Tokyo
        }
    }
}