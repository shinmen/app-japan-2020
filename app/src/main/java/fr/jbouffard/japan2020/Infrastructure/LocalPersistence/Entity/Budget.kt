package fr.jbouffard.japan2020.Infrastructure.LocalPersistence.Entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "budget")
class Budget(
        @ColumnInfo(name = "aggregateUuid") var aggregateUuid: String?,
        @ColumnInfo(name = "day_nb") var dayNb: Long?,
        @ColumnInfo(name = "service") var service: String?,
        @ColumnInfo(name = "rate") var rate: Float = 0.toFloat(),
        @ColumnInfo(name = "service_label") var serviceLabel: String?,
        @PrimaryKey(autoGenerate = true) var id: Int = 0
    ) {
    companion object {
        const val SERVICE_FLIGHT: String = "flight"
        const val SERVICE_ACCOMODATION: String = "overnight"
        const val SERVICE_ACCOMODATION_DISCOUNT: String = "overnight_discount"
        const val SERVICE_RAILPASS: String = "railpass"
    }



}
