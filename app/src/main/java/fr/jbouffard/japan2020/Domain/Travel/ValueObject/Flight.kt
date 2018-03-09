package fr.jbouffard.japan2020.Domain.Travel.ValueObject

import java.util.*

/**
 * Created by julienb on 27/02/18.
 */
data class Flight(
    val flightNumber: Int,
    val departureCity: City,
    val arrivalCity: City,
    val companyName: String,
    val departureDate: Date,
    val arrivalDate: Date,
    val duration: Float,
    val fare: Float,
    val hasStop: Boolean,
    val stopNb: Int
)
