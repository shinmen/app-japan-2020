package fr.jbouffard.japan2020.presenter

import android.util.Log
import fr.jbouffard.japan2020.Domain.Travel.Entity.Holiday
import fr.jbouffard.japan2020.Domain.Travel.Event.RailPassActivated
import fr.jbouffard.japan2020.Domain.Travel.ValueObject.City
import fr.jbouffard.japan2020.Infrastructure.DTO.*
import fr.jbouffard.japan2020.Infrastructure.LocalPersistence.AppDatabase
import fr.jbouffard.japan2020.Infrastructure.LocalPersistence.Entity.Budget
import fr.jbouffard.japan2020.Infrastructure.Repository.ApiInterface
import fr.jbouffard.japan2020.Infrastructure.Repository.HttpClient
import kotlinx.coroutines.*
import org.joda.time.DateTime
import fr.jbouffard.japan2020.Domain.Travel.ValueObject.Visit as DomainVisit

/**
 * Created by julienb on 31/05/18.
 */
class VisitRequestPresenter(
        private val httpClient: HttpClient,
        private val db: AppDatabase
) {

    suspend fun requestVisits(holiday: Holiday): List<Visit>  {
        val retrofit = httpClient.retrofit.baseUrl(ApiInterface.BASE_URL).build()

        val service = retrofit.create<ApiInterface>(ApiInterface::class.java)
        val visitsOffers = service.getVisitsInfo().await()
        val visits = holiday.filterVisitsAvailable(visitsOffers.map { DomainVisit(City(it.city), holiday.currentDate!!) })

        return visitsOffers.filter { visits.map { it.city.name }.contains(it.city) }
    }

    fun visitPlace(holiday: Holiday, destination: String) {
        holiday.goToCity(destination)
        holiday.scheduleVisitCity(destination)
        holiday.getUncommittedChanges()
                .filter { it is RailPassActivated }
                .map{ buildRailpassProjection(it as RailPassActivated) }
    }

    fun finishDay(holiday: Holiday) {
        holiday.wakeUp()
    }

    private fun buildRailpassProjection(event: RailPassActivated) {
        GlobalScope.launch {
            val budgetOvernightEntry = Budget(
                    event.streamId,
                    DateTime(event.railpassPackage.startDate).millis,
                    Budget.SERVICE_RAILPASS,
                    event.railpassPackage.price,
                    "Railpass"
            )
            try {
                val existingBudget = db.budgetDao().findOneByUuidAndTimeAndType(
                        budgetOvernightEntry.aggregateUuid.toString(),
                        budgetOvernightEntry.dayNb!!,
                        Budget.SERVICE_RAILPASS
                )
                if (existingBudget == null) {
                    db.budgetDao().insertOne(budgetOvernightEntry)
                }
            } catch (e: Exception) {
                Log.d("room", e.message)
            }
        }
    }
}