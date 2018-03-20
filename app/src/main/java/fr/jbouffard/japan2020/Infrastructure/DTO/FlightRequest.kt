package fr.jbouffard.japan2020.Infrastructure.DTO

/**
 * Created by julienb on 13/03/18.
 */
data class FlightRequest(
        val originCode: String,
        val destinationCode: String,
        val departureDate: String,
        val returnDate: String
)