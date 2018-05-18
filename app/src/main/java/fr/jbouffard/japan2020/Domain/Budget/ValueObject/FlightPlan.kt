package fr.jbouffard.japan2020.Domain.Budget.ValueObject

/**
 * Created by julienb on 15/05/18.
 */
data class FlightPlan(val goingFlight: FlightInfo, val returnFlight: FlightInfo, val fare: Float)