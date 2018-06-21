package fr.jbouffard.japan2020.Domain.Travel.Entity

import android.os.Parcel
import android.os.Parcelable
import fr.jbouffard.japan2020.Domain.AggregateRoot
import fr.jbouffard.japan2020.Domain.Travel.Event.*
import fr.jbouffard.japan2020.Domain.DomainEvent
import fr.jbouffard.japan2020.Domain.Travel.ValueObject.City
import fr.jbouffard.japan2020.Domain.Travel.ValueObject.Overnight
import fr.jbouffard.japan2020.Domain.Travel.ValueObject.RailpassPackage
import fr.jbouffard.japan2020.Domain.Travel.ValueObject.Visit
import kotlinx.android.parcel.Parcelize
import org.joda.time.DateTime
import org.joda.time.Duration
import org.joda.time.Period
import java.util.*

/**
 * Created by julienb on 26/02/18.
 */
class Holiday(override var uuid: UUID) : AggregateRoot(), Parcelable {
    override val streamId: String by lazy { "Holiday-$uuid" }

    private var airTransportation: AirTransportation? = null

    private var daySchedules: MutableList<Day> = mutableListOf()

    private var railpassPackage: RailpassPackage? = null

    private var startHolidayAt: DateTime? = null

    private var endHolidayAt: DateTime? = null

    var holidayDuration: Long = 0
        get() = Duration(startHolidayAt, endHolidayAt).standardDays

    fun getDateOf(position: Int) = if(position%2 == 0) { daySchedules[position].date } else { daySchedules[position-1].date }

    override fun applyNewEvent(domainEvent: DomainEvent) {
        super.applyNewEvent(domainEvent)
        load(domainEvent as EventList)
    }

    override fun applyEvent(domainEvent: DomainEvent) {
        load(domainEvent as EventList)
    }

    fun selectRoundTrip(goingFlightPlan: FlightPlan, returnFlightPlan: FlightPlan, fare: Float) {
        val airTransportation = AirTransportation(goingFlightPlan, returnFlightPlan, fare)
        airTransportation.selectRoundTrip()

        applyNewEvent(SelectFlightPlan(goingFlightPlan, returnFlightPlan, fare, version, streamId))
        applyNewEvent(PlanHolidayPeriod(
                goingFlightPlan.flightPlan.last().arrivalDate,
                returnFlightPlan.flightPlan.first().departureDate,
                version,
                streamId
        ))
    }

    fun startHolidayPlanning() {
        applyNewEvent(ArrivedInJapan(
            airTransportation!!.goingFlightPlan.flightPlan.last().arrivalDate,
            airTransportation!!.goingFlightPlan.flightPlan.last().arrivalCity,
            version,
            streamId
        ))
    }

    fun selectRailPassPackage(railpass: RailpassPackage) {

    }

    fun wakeUp() {
        val dayAfter = daySchedules.last().date
        dayAfter!!.plus(Period.days(1))
        applyNewEvent(NewDayStarted(dayAfter, version, streamId))
    }

    fun scheduleVisitCity(visit: Visit) {

    }

    fun scheduleStayOver(stay: Overnight) {

    }

    fun goToCity() {
    }

    private fun load(event: EventList) = when (event) {
        is SelectFlightPlan -> loadEvent(event)
        is PlanHolidayPeriod -> loadEvent(event)
        is ArrivedInJapan -> loadEvent(event)
        is NewDayStarted -> loadEvent(event)
    }

    private fun loadEvent(event: SelectFlightPlan) {
        airTransportation = AirTransportation(event.goingFlightPlan, event.returnFlightPlan, event.fare)
        version++
    }

    private fun loadEvent(event: PlanHolidayPeriod) {
        startHolidayAt = event.holidayStartAt
        endHolidayAt = event.holidayEndAt
        version++
    }

    private fun loadEvent(event: ArrivedInJapan) {
        val day = Day()
        day.date = startHolidayAt
        daySchedules.add(day)
        daySchedules.last().visits.add(Visit(event.firstCity, event.arrivedAt.toDate()))
        version++
    }

    private fun loadEvent(event: NewDayStarted) {
        val day = Day()
        day.date = event.date
        daySchedules.add(day)
        version++
    }

    constructor(source: Parcel) : this(source.readSerializable() as UUID) {
        airTransportation = source.readParcelable(AirTransportation::class.java.classLoader)
        source.readList(daySchedules, Day::class.java.classLoader)
        startHolidayAt = DateTime(source.readLong())
        endHolidayAt = DateTime(source.readLong())
    }

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeSerializable(uuid)
        writeParcelable(airTransportation, flags)
        writeList(daySchedules)
        writeLong(startHolidayAt!!.millis)
        writeLong(endHolidayAt!!.millis)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Holiday> = object : Parcelable.Creator<Holiday> {
            override fun createFromParcel(source: Parcel): Holiday = Holiday(source)
            override fun newArray(size: Int): Array<Holiday?> = arrayOfNulls(size)
        }
    }
}