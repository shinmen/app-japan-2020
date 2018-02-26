package fr.jbouffard.japan2020.Domain.Travel.ValueObject

import java.util.*

/**
 * Created by julienb on 26/02/18.
 */
data class RailpassPackage(val packageName: String, val price: Float, val startDate: Date, val endDate: Date)