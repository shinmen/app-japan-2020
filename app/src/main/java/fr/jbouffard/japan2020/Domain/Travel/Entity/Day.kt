package fr.jbouffard.japan2020.Domain.Travel.Entity

import android.os.Parcel
import android.os.Parcelable
import fr.jbouffard.japan2020.Domain.Travel.ValueObject.*
import kotlinx.android.parcel.Parcelize
import org.joda.time.DateTime

/**
 * Created by julienb on 26/02/18.
 */
class Day() : Parcelable {

    var date: DateTime? = null

    var overnight: Overnight? = null
        private set

    var visits: MutableList<Visit> = mutableListOf()

    var movements: MutableList<Movement> = mutableListOf()

    fun scheduleAccommodation(overnight: Overnight) {
        this.overnight = overnight
    }

    fun scheduleVisit(city: String) {
        visits.add(Visit(City(city), date!!))
    }

    fun scheduleMoveTo(move: Movement) {
        movements.add(move)
    }

    constructor(source: Parcel) : this() {
        source.readList(visits, Visit::class.java.classLoader)
        overnight = source.readParcelable(Overnight::class.java.classLoader)
    }

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeList(visits)
        writeParcelable(overnight, flags)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Day> = object : Parcelable.Creator<Day> {
            override fun createFromParcel(source: Parcel): Day = Day(source)
            override fun newArray(size: Int): Array<Day?> = arrayOfNulls(size)
        }
    }
}