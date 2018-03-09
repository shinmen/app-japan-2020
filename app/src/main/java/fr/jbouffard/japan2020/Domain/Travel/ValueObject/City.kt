package fr.jbouffard.japan2020.Domain.Travel.ValueObject

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by julienb on 26/02/18.
 */
data class City(val name: String, val country: String)