package fr.jbouffard.japan2020.Domain.Travel.Entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import fr.jbouffard.japan2020.Domain.Travel.ValueObject.Flight

/**
 * Created by julienb on 27/02/18.
 */
class FlightPlan(@Expose @SerializedName("goingFlight") var goingFlight: Flight, @Expose @SerializedName("returnFlight") var returnFlight: Flight)
