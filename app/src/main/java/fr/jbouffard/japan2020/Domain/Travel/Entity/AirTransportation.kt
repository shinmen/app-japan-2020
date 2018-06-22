package fr.jbouffard.japan2020.Domain.Travel.Entity

import android.os.Parcelable
import fr.jbouffard.japan2020.Domain.Budget.Exception.HolidayTooExpensiveException
import fr.jbouffard.japan2020.Domain.Travel.Exception.NotEnoughTimeToPlanException
import kotlinx.android.parcel.Parcelize
import org.joda.time.DateTime
import org.joda.time.Period

@Parcelize
data class AirTransportation(val goingFlightPlan: FlightPlan, private val returnFlightPlan: FlightPlan, val fare: Float) : Parcelable{

    fun selectRoundTrip() {
        val goingDepartureDate = goingFlightPlan.flightPlan.first().departureDate
        val soonDate = DateTime().apply { plus(Period.days(15))}
        if (soonDate.isAfter(goingDepartureDate)) {
            throw NotEnoughTimeToPlanException("la date de départ est trop proche pour s'organiser")
        }
        val returnArrivalDate = returnFlightPlan.flightPlan.last().departureDate
        goingDepartureDate.plus(Period.days(14))
        if (goingDepartureDate.isAfter(returnArrivalDate)) {
            throw HolidayTooExpensiveException("Nous n'aurons pas assez de tune pour un voyage si long")
        }
    }
}
