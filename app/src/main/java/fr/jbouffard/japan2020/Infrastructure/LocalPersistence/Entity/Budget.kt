package fr.jbouffard.japan2020.Infrastructure.LocalPersistence.Entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "budget")
data class Budget(
        @ColumnInfo(name = "aggregateUuid") private val aggregateUuid: String? = null,
        @ColumnInfo(name = "day_nb") private val dayNb: Int = 1,
        @ColumnInfo(name = "service") private val service: String? = null,
        @ColumnInfo(name = "rate") private val rate: Float = 0.toFloat(),
        @ColumnInfo(name = "service_label") private val serviceLabel: String? = null,
        @PrimaryKey(autoGenerate = true) private val id: Int = 0
    ) {
    companion object {
        const val SERVICE_FLIGHT: String = "flight"
        const val SERVICE_ACCOMODATION: String = "overnight"
        const val SERVICE_ACCOMODATION_DISCOUNT: String = "overnight_discount"
        const val SERVICE_RAILPASS: String = "railpass"
    }
}
