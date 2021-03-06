package fr.jbouffard.japan2020.Domain.Travel.ValueObject

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.joda.time.DateTime
import java.util.*

/**
 * Created by julienb on 26/02/18.
 */
@Parcelize
data class Visit(val city: City, val visitDate: DateTime) : Parcelable