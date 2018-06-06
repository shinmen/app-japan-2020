package fr.jbouffard.japan2020.Domain.Travel.ValueObject

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by julienb on 26/02/18.
 */
@Parcelize
data class City(val name: String, val country: String) : Parcelable