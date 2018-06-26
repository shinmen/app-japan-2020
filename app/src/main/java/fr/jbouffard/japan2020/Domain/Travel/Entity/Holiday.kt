package fr.jbouffard.japan2020.Domain.Travel.Entity

import android.os.Parcel
import android.os.Parcelable
import fr.jbouffard.japan2020.Domain.AggregateRoot
import fr.jbouffard.japan2020.Domain.Travel.Event.*
import fr.jbouffard.japan2020.Domain.DomainEvent
import fr.jbouffard.japan2020.Domain.Travel.Exception.MissingOvernightException
import fr.jbouffard.japan2020.Domain.Travel.ValueObject.*
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

    var currentCity: String? = null

    var currentDate: DateTime? = null
        get() = daySchedules.last().date

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

    fun shouldStartRailpass() {

    }

    fun selectRailPassPackage(railpass: RailpassPackage) {

    }

    fun wakeUp() {
        if (daySchedules.last().overnight == null) {
            throw MissingOvernightException("On dort dehors?")
        }
        val dayAfter = daySchedules.last().date
        dayAfter!!.plus(Period.days(1))
        applyNewEvent(NewDayStarted(dayAfter, version, streamId))
    }

    fun scheduleVisitCity(city: String) {
        daySchedules.last().scheduleVisit(city)
        val visit = Visit(City(city), daySchedules.last().date!!)
        applyNewEvent(VisitedCity(visit, version, streamId))
    }

    fun scheduleStayOver(accommodation: AccommodationAddress, rate: Float, weekDiscount: Float) {
        val overnight = Overnight(accommodation, daySchedules.last().date!!.toDate(), rate, weekDiscount)
        daySchedules.last().scheduleAccommodation(overnight)
        applyNewEvent(SleptInCity(overnight, version, streamId))
    }

    fun goToCity(destination: String) {
        val move = Movement(currentCity!!, destination, daySchedules.last().date!!)
        daySchedules.last().scheduleMoveTo(move)
        applyNewEvent(MovedToCity(move, version, streamId))
    }

    private fun load(event: EventList) = when (event) {
        is SelectFlightPlan -> loadEvent(event)
        is PlanHolidayPeriod -> loadEvent(event)
        is ArrivedInJapan -> loadEvent(event)
        is NewDayStarted -> loadEvent(event)
        is MovedToCity -> loadEvent(event)
        is VisitedCity -> loadEvent(event)
        is SleptInCity -> loadEvent(event)
    }

    private fun loadEvent(event: MovedToCity) {
        daySchedules.last().movements.add(event.move)
        version++
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
        currentCity = event.firstCity.name
        daySchedules.add(day)
        version++
    }

    private fun loadEvent(event: NewDayStarted) {
        val day = Day()
        day.date = event.date
        daySchedules.add(day)
        version++
    }

    private fun loadEvent(event: VisitedCity) {
        daySchedules.last().scheduleVisit(event.visit.city.name)
        version++
    }

    private fun loadEvent(event: SleptInCity) {
        daySchedules.last().scheduleAccommodation(event.overnight)
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