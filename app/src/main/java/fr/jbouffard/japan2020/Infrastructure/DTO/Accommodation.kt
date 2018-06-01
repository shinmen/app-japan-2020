package fr.jbouffard.japan2020.Infrastructure.DTO

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by julienb on 31/05/18.
 */
@Parcelize
data class Accommodation(
        val commercialName: String,
        val propertyType: String,
        val capacity: Int,
        val bedroomsNb: Int,
        val bedNb: Int,
        val bathRoomNb: Int,
        val city: String
    ) : Parcelable