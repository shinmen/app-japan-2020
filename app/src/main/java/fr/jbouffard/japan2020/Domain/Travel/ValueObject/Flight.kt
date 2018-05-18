package fr.jbouffard.japan2020.Domain.Travel.ValueObject

import java.util.*

/**
 * Created by julienb on 27/02/18.
 */
data class Flight(
    val departureCity: City,
    val arrivalCity: City,
    val departureDate: Date,
    val arrivalDate: Date
)
