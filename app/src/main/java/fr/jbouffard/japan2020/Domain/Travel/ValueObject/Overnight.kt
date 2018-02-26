package fr.jbouffard.japan2020.Domain.Travel.ValueObject

import java.util.*

/**
 * Created by julienb on 26/02/18.
 */
data class Overnight(val address : AccommodationAddress, val overnightDate: Date, val rate: Float)