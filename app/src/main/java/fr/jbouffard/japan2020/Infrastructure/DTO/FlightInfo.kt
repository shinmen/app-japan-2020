package fr.jbouffard.japan2020.Infrastructure.DTO

import java.util.*

/**
 * Created by julienb on 13/03/18.
 */
data class FlightInfo(
        val flightNumber: Int, val arrivalDate: Date, val arrivalAirport: String,
        val departureDate: Date, val departureAirport: String
)