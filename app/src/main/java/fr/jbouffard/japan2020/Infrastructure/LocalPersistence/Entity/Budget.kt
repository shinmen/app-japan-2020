package fr.jbouffard.japan2020.Infrastructure.LocalPersistence.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import fr.jbouffard.japan2020.View.PlanHoliday.ViewType

@Entity(tableName = "budget")
class Budget(
        @ColumnInfo(name = "aggregateUuid") var aggregateUuid: String?,
        @ColumnInfo(name = "day_nb") var dayNb: Long?,
        @ColumnInfo(name = "service") var service: String?,
        @ColumnInfo(name = "rate") var rate: Float = 0.toFloat(),
        @ColumnInfo(name = "service_label") var serviceLabel: String?,
        @PrimaryKey(autoGenerate = true) var id: Int = 0
    ): ViewType {

    override fun getDate(): Long {
        return dayNb!!
    }

    override fun getViewType(): Int = VIEW_TYPE

    companion object {
        const val SERVICE_FLIGHT: String = "flight"
        const val SERVICE_ACCOMODATION: String = "overnight"
        const val SERVICE_ACCOMODATION_DISCOUNT: String = "overnight_discount"
        const val SERVICE_RAILPASS: String = "railpass"

        const val VIEW_TYPE = 0
    }


}
