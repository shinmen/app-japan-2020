package fr.jbouffard.japan2020.Presenter

import com.squareup.otto.Bus
import com.squareup.otto.Subscribe
import fr.jbouffard.japan2020.Domain.DomainException
import fr.jbouffard.japan2020.Domain.Travel.Entity.Holiday
import fr.jbouffard.japan2020.Domain.Travel.Event.SelectFlightPlan
import fr.jbouffard.japan2020.Domain.Travel.Event.SleptInCity
import fr.jbouffard.japan2020.Domain.Travel.ValueObject.AccommodationAddress
import fr.jbouffard.japan2020.Domain.Travel.ValueObject.City
import fr.jbouffard.japan2020.Infrastructure.DTO.*
import fr.jbouffard.japan2020.Infrastructure.LocalPersistence.AppDatabase
import fr.jbouffard.japan2020.Infrastructure.LocalPersistence.Dao.BudgetDao
import fr.jbouffard.japan2020.Infrastructure.LocalPersistence.Entity.Budget
import fr.jbouffard.japan2020.Infrastructure.Repository.ApiInterface
import fr.jbouffard.japan2020.Infrastructure.Repository.HttpClient
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import org.joda.time.DateTime
import org.joda.time.Period
import fr.jbouffard.japan2020.Domain.Travel.ValueObject.Visit as DomainVisit

/**
 * Created by julienb on 31/05/18.
 */
class DayRequestPresenter(
        private val httpClient: HttpClient,
        private val eventBus: Bus,
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

        eventBus.register(this)
        holiday.getUncommittedChanges().forEach {
            eventBus.post(it)
        }
    }

    fun finishDay(holiday: Holiday) {
        holiday.wakeUp()
    }

    @Subscribe
    fun buildFlightBudgetProjection(event: SleptInCity) {
        launch {
            val budgetFlightEntry = Budget(
                    event.streamId,
                    DateTime(event.overnight.overnightDate).millis,
                    Budget.SERVICE_ACCOMODATION,
                    event.overnight.rate,
                    "Vols aller/retour"
            )
            try {
                db.budgetDao().insertOne(budgetFlightEntry)
            } catch (e: Exception) {
              e.message
            }
        }

        launch(UI) {
            val resu = db.budgetDao().awaitOne(event.streamId)
        }
    }

    suspend fun BudgetDao.awaitOne(aggregateUuid: String): Budget = async { findByUuid(aggregateUuid) }.await()
}