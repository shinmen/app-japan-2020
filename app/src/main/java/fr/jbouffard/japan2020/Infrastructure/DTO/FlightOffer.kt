package fr.jbouffard.japan2020.Infrastructure.DTO

/**
 * Created by julienb on 13/03/18.
 */
data class FlightOffer(val goingFlight: TripInfo, val returnFlight: TripInfo, val totalRatePerAdult: Float)