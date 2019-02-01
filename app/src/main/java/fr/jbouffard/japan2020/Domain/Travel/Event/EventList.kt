package fr.jbouffard.japan2020.Domain.Travel.Event


import android.os.Parcelable
import fr.jbouffard.japan2020.Domain.DomainEvent
import fr.jbouffard.japan2020.Domain.Travel.Entity.FlightPlan
import fr.jbouffard.japan2020.Domain.Travel.ValueObject.*
import kotlinx.android.parcel.Parcelize
import org.joda.time.DateTime
import java.util.*

/**
 * Created by julienb on 26/02/18.
 */
sealed class EventList
@Parcelize
data class PlanHolidayPeriod(val holidayStartAt: DateTime, val holidayEndAt: DateTime, val version: Int, override val streamId: String): Parcelable, EventList(), DomainEvent
@Parcelize
data class SelectFlightPlan(val goingFlightPlan: FlightPlan, val returnFlightPlan: FlightPlan, val fare: Float, val version: Int, override val streamId: String): Parcelable, EventList(), DomainEvent
@Parcelize
data class ArrivedInJapan(val arrivedAt: DateTime, val firstCity: City, val version: Int, override val streamId: String): Parcelable, EventList(), DomainEvent
@Parcelize
data class NewDayStarted(val date: DateTime, val version: Int, override val streamId: String): Parcelable, EventList(), DomainEvent
@Parcelize
data class MovedToCity(val move: Movement, val version: Int, override val streamId: String): Parcelable, EventList(), DomainEvent
@Parcelize
data class VisitedCity(val visit: Visit, val version: Int, override val streamId: String): Parcelable, EventList(), DomainEvent
@Parcelize
data class SleptInCity(val overnight: Overnight, val version: Int, override val streamId: String): Parcelable, EventList(), DomainEvent
@Parcelize
data class RailPassActivated(val railpassPackage: RailpassPackage, val version: Int, override val streamId: String): Parcelable, EventList(), DomainEvent



