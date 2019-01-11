package fr.jbouffard.japan2020.Presenter

import fr.jbouffard.japan2020.Infrastructure.LocalPersistence.AppDatabase
import fr.jbouffard.japan2020.Infrastructure.LocalPersistence.Dao.BudgetDao
import fr.jbouffard.japan2020.Infrastructure.LocalPersistence.Entity.Budget
import fr.jbouffard.japan2020.Infrastructure.Repository.HttpClient
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class BudgetPresenter(
        private val httpClient: HttpClient,
        private val db: AppDatabase
)  {
    suspend fun getOnGoingBudget(aggregateUuid: String): List<Budget> {
        return db.budgetDao().awaitOne(aggregateUuid)
    }

    private suspend fun BudgetDao.awaitOne(aggregateUuid: String): List<Budget> = GlobalScope.async { findByUuid(aggregateUuid) }.await()
}