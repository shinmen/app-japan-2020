package fr.jbouffard.japan2020.Domain.Travel.ValueObject

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.joda.time.DateTime
import java.util.*

/**
 * Created by julienb on 27/02/18.
 */
@Parcelize
data class Flight(
    val departureCity: City,
    val departureDate: DateTime,
    val arrivalCity: City,
    val arrivalDate: DateTime,
    val duration: Float,
    val flightNumber: String
) : Parcelable
