package fr.jbouffard.japan2020.Domain.Utils

import com.mapbox.mapboxsdk.geometry.LatLng

/**
 * Created by julienb on 24/05/18.
 */
class DistanceCalculator {

    companion object {
        private const val EARTH_RADIUS = 6371 // Radius of the earth

        fun distance(origin: LatLng, destination: LatLng): Double {

            val latDistance = Math.toRadians(destination.latitude - origin.latitude)
            val lonDistance = Math.toRadians(destination.longitude - origin.longitude)
            val a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + (Math.cos(Math.toRadians(origin.latitude)) * Math.cos(Math.toRadians(destination.latitude)) * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2))
            val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
            var distance = EARTH_RADIUS.toDouble() * c * 1000.0 // convert to meters

            val height = 0.0

            distance = Math.pow(distance, 2.0) + Math.pow(height, 2.0)

            return Math.sqrt(distance)
        }
    }
}