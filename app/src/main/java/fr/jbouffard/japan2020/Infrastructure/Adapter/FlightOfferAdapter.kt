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


/*    fun toTravelDomain(): TravelFlightPlan {

        val (goingDepartureCity, goingArrivalCity, returnDepartureCity, returnArrivalCity) = getCitiesFromAirport()

        val goingFlight = Flight(
                goingDepartureCity,
                goingArrivalCity,
                flightOffer.goingFlight.flights.first().departureDate.toDate(),
                flightOffer.goingFlight.flights.last().arrivalDate.toDate()
        )

        val returnFlight = Flight(
                returnDepartureCity,
                returnArrivalCity,
                flightOffer.returnFlight.flights.first().departureDate.toDate(),
                flightOffer.returnFlight.flights.last().arrivalDate.toDate()
        )

        return TravelFlightPlan(goingFlight, returnFlight)
    }

    fun toBudgetDomain(): BudgetFlightPlan {
        val (goingDepartureCity, goingArrivalCity, returnDepartureCity, returnArrivalCity) = getCitiesFromAirport()

        val goingFlight = FlightInfo(
                flightOffer.goingFlight.flights.first().flightNumber,
                goingDepartureCity,
                goingArrivalCity,
                flightOffer.goingFlight.companyName,
                flightOffer.goingFlight.flights.first().departureDate.toDate(),
                flightOffer.goingFlight.flights.last().arrivalDate.toDate(),
                flightOffer.goingFlight.duration,
                flightOffer.goingFlight.flights.isNotEmpty(),
                flightOffer.goingFlight.flights.size

        )
        val returnFlight = FlightInfo(
                flightOffer.returnFlight.flights.first().flightNumber,
                returnDepartureCity,
                returnArrivalCity,
                flightOffer.returnFlight.companyName,
                flightOffer.returnFlight.flights.first().departureDate.toDate(),
                flightOffer.returnFlight.flights.last().arrivalDate.toDate(),
                flightOffer.returnFlight.duration,
                flightOffer.returnFlight.flights.isNotEmpty(),
                flightOffer.returnFlight.flights.size
        )

        return BudgetFlightPlan(goingFlight, returnFlight, flightOffer.totalRatePerAdult)
    }

    private fun getCitiesFromAirport(): List<City> {
        val goingDepartureCity = City(
                flightOffer.goingFlight.flights.first().departureAirport.city,
                flightOffer.goingFlight.flights.first().departureAirport.country
        )
        val goingArrivalCity = City(
                flightOffer.goingFlight.flights.last().arrivalAirport.city,
                flightOffer.goingFlight.flights.last().arrivalAirport.country
        )

        val returnDepartureCity = City(
                flightOffer.returnFlight.flights.first().departureAirport.city,
                flightOffer.returnFlight.flights.first().departureAirport.country
        )
        val returnArrivalCity = City(
                flightOffer.returnFlight.flights.last().arrivalAirport.city,
                flightOffer.returnFlight.flights.last().arrivalAirport.country
        )

        return listOf(goingDepartureCity, goingArrivalCity, returnDepartureCity, returnArrivalCity)
    }*/

}