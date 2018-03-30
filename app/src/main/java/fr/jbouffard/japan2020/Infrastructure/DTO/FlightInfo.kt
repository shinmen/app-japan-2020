package fr.jbouffard.japan2020.Infrastructure.DTO

import org.joda.time.DateTime
import java.util.*

/**
 * Created by julienb on 13/03/18.
 */
data class FlightInfo(
        val flightNumber: Int, val arrivalDate: DateTime, val arrivalAirport: String,
        val departureDate: DateTime, val departureAirport: String
)