package fr.jbouffard.japan2020.Domain.Travel.Entity

import android.os.Parcelable
import fr.jbouffard.japan2020.Domain.Travel.ValueObject.Flight
import kotlinx.android.parcel.Parcelize

/**
 * Created by julienb on 27/02/18.
 */
@Parcelize
data class FlightPlan(val flightPlan: List<Flight>, val companyName: String) : Parcelable
