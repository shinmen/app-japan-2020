package fr.jbouffard.japan2020.Infrastructure.Adapter

import fr.jbouffard.japan2020.Domain.Travel.ValueObject.Flight
import fr.jbouffard.japan2020.View.PlanHoliday.ViewType
import fr.jbouffard.japan2020.View.PlanHoliday.DetailPlan.DetailFlightSchedule
import org.joda.time.DateTime

class FlightAdapter {
    fun toDetailFlights(
            takeOffDate: DateTime,
            flights : List<Flight>,
            company: String,
            fare: Float?
    ): List<ViewType> {
        return flights.map { flight-> toFlight(takeOffDate, flight, company, fare) }
    }

    private fun toFlight(takeOffDate: DateTime, flight: Flight, company: String, fare: Float?): ViewType {
        return DetailFlightSchedule(takeOffDate, flight, company, fare)
    }
}