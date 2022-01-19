package fr.jbouffard.japan2020.Infrastructure.LocalPersistence.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import fr.jbouffard.japan2020.Infrastructure.LocalPersistence.Entity.Budget

@Dao
interface BudgetDao {
    @Insert
    fun insertOne(entry: Budget)

    @Query("DELETE FROM budget WHERE aggregateUuid = :uuid")
    fun deleteByUuid(uuid: String)

    @Query("SELECT * FROM budget WHERE aggregateUuid = :uuid")
    fun findByUuid(uuid: String): List<Budget>

    @Query("SELECT * FROM budget WHERE aggregateUuid = :uuid AND day_nb = :dayNb AND service = :service")
    fun findOneByUuidAndTimeAndType(uuid: String, dayNb: Long, service: String): Budget?

    @Query("DELETE FROM budget")
    fun truncate()
}