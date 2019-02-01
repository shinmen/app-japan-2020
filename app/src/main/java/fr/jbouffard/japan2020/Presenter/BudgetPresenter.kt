package fr.jbouffard.japan2020.Presenter

import android.support.v4.util.SparseArrayCompat
import fr.jbouffard.japan2020.Infrastructure.LocalPersistence.AppDatabase
import fr.jbouffard.japan2020.Infrastructure.LocalPersistence.Dao.BudgetDao
import fr.jbouffard.japan2020.Infrastructure.LocalPersistence.Entity.Budget
import fr.jbouffard.japan2020.Infrastructure.Repository.HttpClient
import fr.jbouffard.japan2020.View.PlanHoliday.DateDayDelegateAdapter
import fr.jbouffard.japan2020.View.PlanHoliday.Budget.BudgetDelegateAdapter
import fr.jbouffard.japan2020.View.PlanHoliday.DetailDaySeparator
import fr.jbouffard.japan2020.View.PlanHoliday.SeparatorDelegateAdapter
import fr.jbouffard.japan2020.View.PlanHoliday.ViewType
import fr.jbouffard.japan2020.View.PlanHoliday.DateDetailDay
import fr.jbouffard.japan2020.View.PlanHoliday.DetailDayRecyclerViewAdapter
import fr.jbouffard.japan2020.View.PlanHoliday.DetailPlan.DetailRailpassDelegateAdapter
import fr.jbouffard.japan2020.View.PlanHoliday.DetailPlan.DetailRailpassPackage
import fr.jbouffard.japan2020.View.PlanHoliday.DetailPlan.DetailTotalPrice
import fr.jbouffard.japan2020.View.PlanHoliday.DetailPlan.DetailTotalPriceDelegateAdapter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class BudgetPresenter(private val db: AppDatabase)  {
    suspend fun getOnGoingBudget(aggregateUuid: String): List<Budget> {
        return db.budgetDao().awaitOne(aggregateUuid)
    }

    fun mapBudget(budget: List<ViewType>): List<ViewType> {
        return budget
                .groupBy { it.getDate() }
                .flatMap { val lines = it.value
                        .toMutableList()
                    lines.add(DetailDaySeparator())
                    lines.add(0, DateDetailDay(it.key))
                    lines
                }
                .toList()
    }

    fun getDelegateAdapters(): SparseArrayCompat<DetailDayRecyclerViewAdapter.ViewTypeDelegateAdapter> {
        val delegates = SparseArrayCompat<DetailDayRecyclerViewAdapter.ViewTypeDelegateAdapter>(3)
        delegates.append(Budget.VIEW_TYPE, BudgetDelegateAdapter())
        delegates.append(DetailDaySeparator.VIEW_TYPE, SeparatorDelegateAdapter())
        delegates.append(DateDetailDay.VIEW_TYPE, DateDayDelegateAdapter())

        return delegates
    }

    private suspend fun BudgetDao.awaitOne(aggregateUuid: String): List<Budget> = GlobalScope.async { findByUuid(aggregateUuid) }.await()
}