package fr.jbouffard.japan2020.Infrastructure.DTO

/**
 * Created by julienb on 13/03/18.
 */
data class TripInfo(val duration: Float, val companyName: String, val flights: List<FlightInfo>)