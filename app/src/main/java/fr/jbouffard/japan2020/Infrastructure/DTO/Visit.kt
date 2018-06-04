package fr.jbouffard.japan2020.Infrastructure.DTO

import android.os.Parcelable
import com.mapbox.mapboxsdk.geometry.LatLng
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Visit(val city: String, val geolocation: LatLng, val tourismInfo: String): Parcelable