package fr.jbouffard.japan2020.Infrastructure.LocalPersistence.Dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import fr.jbouffard.japan2020.Infrastructure.LocalPersistence.Entity.Budget

@Dao
interface BudgetDao {
    @Insert
    fun insertOne(entry: Budget)

    @Query("DELETE FROM budget WHERE aggregateUuid = :uuid")
    fun deleteByUuid(uuid: String)

    @Query("SELECT * FROM budget WHERE aggregateUuid = :uuid")
    fun findByUuid(uuid: String)
}