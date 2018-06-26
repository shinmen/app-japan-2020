package fr.jbouffard.japan2020.Domain.Travel.ValueObject

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by julienb on 26/02/18.
 */
@Parcelize
data class AccommodationAddress(val name: String, val city: City) : Parcelable