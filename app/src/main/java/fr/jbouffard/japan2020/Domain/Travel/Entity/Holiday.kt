package fr.jbouffard.japan2020.Domain.Travel.Entity

import android.os.Parcel
import android.os.ParcelUuid
import android.os.Parcelable
import fr.jbouffard.japan2020.Domain.AggregateRoot
import fr.jbouffard.japan2020.Domain.Travel.Event.*
import fr.jbouffard.japan2020.Domain.DomainEvent
import fr.jbouffard.japan2020.Domain.Travel.Exception.MissingOvernightException
import fr.jbouffard.japan2020.Domain.Travel.Exception.MustReturnToCityOfDepartureException
import fr.jbouffard.japan2020.Domain.Travel.Exception.RailpassExpiredException
import fr.jbouffard.japan2020.Domain.Travel.ValueObject.*
import org.joda.time.DateTime
import org.joda.time.Duration
import org.joda.time.Period
import java.util.*
import kotlin.collections.ArrayList

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
        get() = Duration(startHolidayAt, endHolidayAt?.plusDays(1)).standardDays

    private var currentCity: String? = null

    var currentDate: DateTime? = null
        get() = daySchedules.last().date

    override fun applyNewEvent(domainEvent: DomainEvent) {
        super.applyNewEvent(domainEvent)
        load(domainEvent as EventList)
    }

    override fun applyEvent(domainEvent: DomainEvent) {
        load(domainEvent as EventList)
    }

    fun filterVisitsAvailable(visits: List<Visit> ): List<Visit> {
        railpassPackage?.let {
            if (!it.isRailPassStillActive(currentDate!!)) {
                return visits.filter { it.city.name == airTransportation!!.returnFlightPlan.flightPlan.first().departureCity.name }
            }
        }

        return visits
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

    fun wakeUp() {
        if (daySchedules.last().overnight == null) {
            throw MissingOvernightException("On dort dehors?")
        }
        val dayAfter = currentDate!!.plus(Period.days(1))
        applyNewEvent(NewDayStarted(dayAfter, version, streamId))
    }

    fun scheduleVisitCity(city: String) {
        val visit = Visit(City(city), daySchedules.last().date!!)
        applyNewEvent(VisitedCity(visit, version, streamId))
    }

    fun scheduleStayOver(accommodation: AccommodationAddress, rate: Float, weekDiscount: Float, picture: String) {
        railpassPackage?.let {
            if (it.isEndSameDayAs(currentDate!!) &&
                accommodation.commercialCityName != airTransportation!!.returnFlightPlan.flightPlan.first().departureCity.name
            ) {
              throw MustReturnToCityOfDepartureException(
                      "Le railpass se termine aujourd'hui, il faut retourner dans la ville de départ du vol retour"
              )
            }
        }
        val overnight = Overnight(accommodation, currentDate!!.toDate(), rate, weekDiscount, picture)
        applyNewEvent(SleptInCity(overnight, version, streamId))
    }

    fun goToCity(destination: String) {
        if (currentCity!! == destination) {
            return
        }
        activateRailPassAtFirstCityChange()
        railpassPackage?.let {
            if (!it.isRailPassStillActive(currentDate!!)) {
                throw RailpassExpiredException("Impossible de s\'y rendre, le railpass a expiré")
            }
        }
        val move = Movement(currentCity!!, destination, currentDate!!)
        applyNewEvent(MovedToCity(move, version, streamId))
    }

    fun getPlannedDays(): List<Day> {
        return ArrayList<Day>(daySchedules)
    }

    fun getRailPass(): RailpassPackage? {
        return railpassPackage?.copy()
    }

    fun getAirTransportation(): AirTransportation {
        return airTransportation!!.copy()
    }

    fun getBudget(): Float {
        var budget = getAirTransportation().fare
        budget = budget.plus(getPlannedDays().map { it.overnight!!.rate }.sum())
        getRailPass()?.let {
            budget = budget.plus(it.price)
        }

        return budget
    }

    private fun activateRailPassAtFirstCityChange() {
        if (railpassPackage == null) {
            activateRailPass()
        }
    }

    private fun activateRailPass() {
        val endDate = currentDate!!.plus(Period.days(RailpassPackage.PACKAGE_DURATION - 1))
        railpassPackage = RailpassPackage(startDate = currentDate!!.toDate(), endDate = endDate.toDate())
        applyNewEvent(RailPassActivated(railpassPackage!!, version, streamId))
    }

    private fun load(event: EventList) = when (event) {
        is SelectFlightPlan -> loadEvent(event)
        is PlanHolidayPeriod -> loadEvent(event)
        is ArrivedInJapan -> loadEvent(event)
        is NewDayStarted -> loadEvent(event)
        is MovedToCity -> loadEvent(event)
        is VisitedCity -> loadEvent(event)
        is SleptInCity -> loadEvent(event)
        is RailPassActivated -> loadEvent(event)
    }

    private fun loadEvent(event: MovedToCity) {
        daySchedules.last().scheduleMoveTo(event.move)
        currentCity = event.move.destination
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
        currentDate = startHolidayAt
        currentCity = event.firstCity.name
        daySchedules.add(day)
        version++
    }

    private fun loadEvent(event: NewDayStarted) {
        val day = Day()
        day.date = event.date
        currentDate = event.date
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

    private fun loadEvent(event: RailPassActivated) {
        railpassPackage = event.railpassPackage
        version++
    }

    constructor(source: Parcel) : this(source.readSerializable() as UUID) {
        /*airTransportation = source.readParcelable(AirTransportation::class.java.classLoader)
        source.readList(daySchedules, Day::class.java.classLoader)
        startHolidayAt = DateTime(source.readLong())
        endHolidayAt = DateTime(source.readLong())
        railpassPackage = source.readParcelable(RailpassPackage::class.java.classLoader) as RailpassPackage?*/
        source.readList(changes, EventList::class.java.classLoader)
        this.replayHistory(changes)
    }

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeSerializable(uuid)
        /*writeParcelable(airTransportation, flags)
        writeList(daySchedules)
        writeLong(startHolidayAt!!.millis)
        writeLong(endHolidayAt!!.millis)
        if (railpassPackage != null) {
            writeParcelable(railpassPackage, flags)
        }*/
        writeList(changes)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Holiday> = object : Parcelable.Creator<Holiday> {
            override fun createFromParcel(source: Parcel): Holiday = Holiday(source)
            override fun newArray(size: Int): Array<Holiday?> = arrayOfNulls(size)
        }
    }
}