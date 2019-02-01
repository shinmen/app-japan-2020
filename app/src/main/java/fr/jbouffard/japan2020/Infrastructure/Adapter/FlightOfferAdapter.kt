package fr.jbouffard.japan2020.Infrastructure.Adapter

import fr.jbouffard.japan2020.Domain.Budget.ValueObject.FlightInfo
import fr.jbouffard.japan2020.Domain.Travel.Entity.FlightPlan
import fr.jbouffard.japan2020.Domain.Travel.ValueObject.City
import fr.jbouffard.japan2020.Domain.Travel.ValueObject.Flight
import fr.jbouffard.japan2020.Infrastructure.DTO.FlightOffer
import fr.jbouffard.japan2020.Infrastructure.DTO.TripInfo

/**
 * Created by julienb on 21/05/18.
 */
class FlightOfferAdapter {

    fun toFlightPlan(tripInfo: TripInfo): FlightPlan {
        return FlightPlan(tripInfo.flights.map {
            Flight(
                    City(it.departureAirport.city, it.departureAirport.country),
                    it.departureDate,
                    City(it.arrivalAirport.city, it.arrivalAirport.country),
                    it.arrivalDate,
                    it.duration,
                    it.flightNumber.toString()
            )
        }, tripInfo.companyName)
    }
}