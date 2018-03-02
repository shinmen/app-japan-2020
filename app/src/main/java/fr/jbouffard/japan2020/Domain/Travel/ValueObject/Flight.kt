package fr.jbouffard.japan2020.Domain.Travel.ValueObject

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*
import javax.xml.datatype.Duration

/**
 * Created by julienb on 27/02/18.
 */
class Flight {
    @SerializedName("flightNumber")
    @Expose
    var flightNumber: Int

    @SerializedName("departureCity")
    @Expose
    var departureCity: City

    @SerializedName("arrivalCity")
    @Expose
    var arrivalCity: City

    @SerializedName("companyName")
    @Expose
    var companyName: String

    @SerializedName("departureDate")
    @Expose
    var departureDate: Date

    @SerializedName("arrivalDate")
    @Expose
    var arrivalDate: Date

    @SerializedName("duration")
    @Expose
    var duration: Float

    @SerializedName("fare")
    @Expose
    var fare: Float

    @SerializedName("hasStop")
    @Expose
    var hasStop: Boolean

    @SerializedName("stopNb")
    @Expose
    var stopNb: Int

    constructor(
            flightNumber: Int, departureCity: City, arrivalCity: City, companyName: String,
            departureDate: Date, arrivalDate: Date, duration: Float, fare: Float, hasStop: Boolean,
            stopNb: Int
            ) {
        this.flightNumber = flightNumber
        this.departureCity = departureCity
        this.arrivalCity = arrivalCity
        this.companyName = companyName
        this.departureDate = departureDate
        this.arrivalDate = arrivalDate
        this.duration = duration
        this.fare = fare
        this.hasStop = hasStop
        this.stopNb = stopNb
    }
}