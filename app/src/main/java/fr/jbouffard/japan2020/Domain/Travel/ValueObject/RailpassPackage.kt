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
        private const val PACKAGE_7_NAME = "Railpass 7 jours"
        private const val PACKAGE_7_RATE = 224.toFloat()
        const val PACKAGE_DURATION = 7
    }

    fun isEndSameDayAs(day: DateTime): Boolean {
        return DateTime(endDate).toLocalDate() == day.toLocalDate()
    }

    fun isRailPassStillActive(day: DateTime): Boolean {
        return DateTime(endDate).toLocalDate() >=  day.toLocalDate()
    }
}