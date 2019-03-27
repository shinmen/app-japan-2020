package fr.jbouffard.japan2020.Domain.Travel.Entity

import android.os.Parcelable
import fr.jbouffard.japan2020.Domain.Budget.Exception.HolidayTooExpensiveException
import fr.jbouffard.japan2020.Domain.Travel.Exception.NotEnoughTimeToPlanException
import kotlinx.android.parcel.Parcelize
import org.joda.time.DateTime
import org.joda.time.Period

@Parcelize
data class AirTransportation(val goingFlightPlan: FlightPlan, val returnFlightPlan: FlightPlan, val fare: Float) : Parcelable{

    fun selectRoundTrip() {
        val goingDepartureDate = goingFlightPlan.flightPlan.first().departureDate
        val soonDate = DateTime().apply { plus(Period.days(MIN_DAY_BEFORE_DEPARTURE))}
        if (goingDepartureDate.isBefore(soonDate)) {
            throw NotEnoughTimeToPlanException("la date de départ est trop proche pour s'organiser")
        }
        val returnArrivalDate = returnFlightPlan.flightPlan.last().departureDate
        val maxDate = goingDepartureDate.plus(Period.days(14))
        if (maxDate.isBefore(returnArrivalDate)) {
            throw HolidayTooExpensiveException("Nous n'aurons pas assez de tune pour un voyage si long")
        }
    }

    companion object {
        const val MIN_DAY_BEFORE_DEPARTURE = 30
    }
}
