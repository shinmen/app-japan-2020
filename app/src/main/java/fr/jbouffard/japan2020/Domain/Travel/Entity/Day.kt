package fr.jbouffard.japan2020.Domain.Travel.Entity

import android.os.Parcel
import android.os.Parcelable
import fr.jbouffard.japan2020.Domain.Travel.ValueObject.Overnight
import fr.jbouffard.japan2020.Domain.Travel.ValueObject.Visit
import kotlinx.android.parcel.Parcelize

/**
 * Created by julienb on 26/02/18.
 */
class Day() : Parcelable {
    var overnight: Overnight? = null
        private set

    var visits: MutableList<Visit> = mutableListOf()

    fun scheduleAccomodation(overnight: Overnight) {
        this.overnight = overnight
    }

    fun scheduleVisit(visit: Visit) {
        visits.add(visit)
    }

    constructor(source: Parcel) : this() {

    }

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {

    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Day> = object : Parcelable.Creator<Day> {
            override fun createFromParcel(source: Parcel): Day = Day(source)
            override fun newArray(size: Int): Array<Day?> = arrayOfNulls(size)
        }
    }
}