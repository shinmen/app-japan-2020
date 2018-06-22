package fr.jbouffard.japan2020.Domain.Travel.ValueObject

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.joda.time.DateTime

/**
 * Created by julienb on 22/06/18.
 */
@Parcelize
data class Movement(val origin: String, val destination: String, val date: DateTime): Parcelable