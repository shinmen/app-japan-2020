package fr.jbouffard.japan2020.Presenter

import android.util.Log
import fr.jbouffard.japan2020.Domain.Travel.Entity.Holiday
import fr.jbouffard.japan2020.Domain.Travel.Event.SleptInCity
import fr.jbouffard.japan2020.Domain.Travel.ValueObject.AccommodationAddress
import fr.jbouffard.japan2020.Domain.Travel.ValueObject.City
import fr.jbouffard.japan2020.Infrastructure.DTO.*
import fr.jbouffard.japan2020.Infrastructure.LocalPersistence.AppDatabase
import fr.jbouffard.japan2020.Infrastructure.LocalPersistence.Dao.BudgetDao
import fr.jbouffard.japan2020.Infrastructure.LocalPersistence.Entity.Budget
import fr.jbouffard.japan2020.Infrastructure.Repository.ApiInterface
import fr.jbouffard.japan2020.Infrastructure.Repository.HttpClient
import kotlinx.coroutines.*
import org.joda.time.DateTime
import org.joda.time.Period
import fr.jbouffard.japan2020.Domain.Travel.ValueObject.Visit as DomainVisit

/**
 * Created by julienb on 31/05/18.
 */
class DayRequestPresenter(
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

    suspend fun requestOvernightsOffers(date: DateTime, city: City): List<OvernightOffer>  {
        val retrofit = httpClient.retrofit.baseUrl(ApiInterface.BASE_URL).build()

        val service = retrofit.create<ApiInterface>(ApiInterface::class.java)
        val request = OvernightRequest(
                date.toString("y-M-d 12:00"),
                date.plus(Period.days(1)).toString("y-M-d 12:00"),
                6,
                city.name
        )
        return service.getOvernightOffers(request).await()
    }

    fun visitPlace(holiday: Holiday, destination: String) {
        holiday.goToCity(destination)
        holiday.scheduleVisitCity(destination)
    }

    fun sleepIn(holiday: Holiday, overnightOffer: OvernightOffer) {
        val accommodation = AccommodationAddress(
                overnightOffer.accommodation.commercialName,
                City(overnightOffer.accommodation.city),
                overnightOffer.accommodation.queryCity
                )
        holiday.goToCity(accommodation.commercialCityName)
        holiday.scheduleStayOver(accommodation, overnightOffer.pricePerPax, overnightOffer.weekReduction)

        holiday.getUncommittedChanges()
                .filter { it is SleptInCity }
                .map{ buildStayProjection(it as SleptInCity) }
    }

    fun finishDay(holiday: Holiday) {
        holiday.wakeUp()
    }

    suspend fun getOnGoingBudget(aggregateUuid: String): List<Budget> {
        return db.budgetDao().awaitOne(aggregateUuid)
    }

    fun buildStayProjection(event: SleptInCity) {
        GlobalScope.launch {
            val budgetOvernightEntry = Budget(
                    event.streamId,
                    DateTime(event.overnight.overnightDate).millis,
                    Budget.SERVICE_ACCOMODATION,
                    event.overnight.rate,
                    "Nuit"
            )
            try {
                val existingBudget = db.budgetDao().findOneByUuidAndTimeAndType(
                        budgetOvernightEntry.aggregateUuid.toString(),
                        budgetOvernightEntry.dayNb!!,
                        Budget.SERVICE_ACCOMODATION
                )
                if (existingBudget == null) {
                    db.budgetDao().insertOne(budgetOvernightEntry)
                }
            } catch (e: Exception) {
              Log.d("room", e.message)
            }
        }
    }

    private suspend fun BudgetDao.awaitOne(aggregateUuid: String): List<Budget> = GlobalScope.async { findByUuid(aggregateUuid) }.await()
}