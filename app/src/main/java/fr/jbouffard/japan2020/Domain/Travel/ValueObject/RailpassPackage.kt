package fr.jbouffard.japan2020.Domain.Travel.ValueObject

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * Created by julienb on 26/02/18.
 */
@Parcelize
data class RailpassPackage(val packageName: String, val price: Float, val startDate: Date, val endDate: Date) : Parcelable