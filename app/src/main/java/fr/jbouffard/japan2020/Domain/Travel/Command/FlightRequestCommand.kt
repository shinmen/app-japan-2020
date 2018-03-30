package fr.jbouffard.japan2020.Domain.Travel.Command

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.joda.time.DateTime

/**
 * Created by julienb on 20/03/18.
 */
@Parcelize
data class FlightRequestCommand(
        val origin: String,
        val goingDate: DateTime,
        val destination: String,
        val returnDate: DateTime
) : Parcelable