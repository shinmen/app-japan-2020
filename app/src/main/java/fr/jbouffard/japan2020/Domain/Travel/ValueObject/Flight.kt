package fr.jbouffard.japan2020.Domain.Travel.ValueObject

import org.joda.time.DateTime
import java.util.*

/**
 * Created by julienb on 27/02/18.
 */
data class Flight(
    val departureCity: City,
    val departureDate: DateTime,
    val arrivalCity: City,
    val arrivalDate: DateTime,
    val duration: Float,
    val flightNumber: String
)
