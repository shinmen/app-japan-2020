package fr.jbouffard.japan2020.Domain.Travel.Entity

import fr.jbouffard.japan2020.Domain.Travel.ValueObject.Flight

/**
 * Created by julienb on 27/02/18.
 */
data class FlightPlan(val goingFlight: Flight, val returnFlight: Flight)
