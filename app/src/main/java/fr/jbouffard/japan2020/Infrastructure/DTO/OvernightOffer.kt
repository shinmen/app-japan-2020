package fr.jbouffard.japan2020.Infrastructure.DTO

import android.os.Parcelable
import com.mapbox.mapboxsdk.geometry.LatLng
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OvernightOffer(
        val accommodation: Accommodation,
        val geolocation: LatLng,
        val pricePerPax: Float,
        val weekReduction: Float,
        val thumbnailUrl: String,
        val otherThumbnails: List<String>
) : Parcelable