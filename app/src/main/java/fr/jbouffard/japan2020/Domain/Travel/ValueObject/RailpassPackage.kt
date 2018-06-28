package fr.jbouffard.japan2020.Domain.Travel.ValueObject

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.joda.time.DateTime
import java.util.*

/**
 * Created by julienb on 26/02/18.
 */
@Parcelize
data class RailpassPackage(var packageName: String = PACKAGE_7_NAME, val price: Float = PACKAGE_7_RATE, val startDate: Date, val endDate: Date) : Parcelable {
    companion object {
        private const val PACKAGE_7_NAME = "7 days"
        private const val PACKAGE_7_RATE = 224.toFloat()
    }

    fun isEndSameDayAs(day: DateTime): Boolean {
        return DateTime(endDate).dayOfYear() == day.dayOfYear()
    }

    fun isRailPassStillActive(day: DateTime): Boolean {
        return DateTime(endDate).dayOfYear().dateTime <=  day.dayOfYear().dateTime
    }
}