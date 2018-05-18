package fr.jbouffard.japan2020.Domain.Budget.ValueObject

import fr.jbouffard.japan2020.Domain.Travel.ValueObject.City
import java.util.*

/**
 * Created by julienb on 15/05/18.
 */
data class FlightInfo(
        val flightNumber: Int,
        val departureCity: City,
        val arrivalCity: City,
        val companyName: String,
        val departureDate: Date,
        val arrivalDate: Date,
        val duration: Float,
        val hasStop: Boolean,
        val stopNb: Int
)
