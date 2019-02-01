package fr.jbouffard.japan2020.Domain.Travel.ValueObject

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * Created by julienb on 26/02/18.
 */
@Parcelize
data class Overnight(val address : AccommodationAddress, val overnightDate: Date, val rate: Float, val weekDiscount: Float, val picture: String) : Parcelable