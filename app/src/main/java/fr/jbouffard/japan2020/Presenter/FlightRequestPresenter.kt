package fr.jbouffard.japan2020.Presenter

import android.util.Log
import fr.jbouffard.japan2020.Domain.Budget.Entity.BudgetOrganisation
import fr.jbouffard.japan2020.Domain.DomainException
import fr.jbouffard.japan2020.Domain.RepositoryInterface
import fr.jbouffard.japan2020.Domain.Travel.Entity.Holiday
import fr.jbouffard.japan2020.Domain.Travel.Event.EventList
import fr.jbouffard.japan2020.Domain.Travel.Event.SelectFlightPlan
import fr.jbouffard.japan2020.Infrastructure.Adapter.FlightOfferAdapter
import fr.jbouffard.japan2020.Infrastructure.Command.FlightRequestCommand
import fr.jbouffard.japan2020.Infrastructure.DTO.FlightOffer
import fr.jbouffard.japan2020.Infrastructure.DTO.FlightRequest
import fr.jbouffard.japan2020.Infrastructure.LocalPersistence.AppDatabase
import fr.jbouffard.japan2020.Infrastructure.LocalPersistence.Dao.BudgetDao
import fr.jbouffard.japan2020.Infrastructure.LocalPersistence.Entity.Budget
import fr.jbouffard.japan2020.Infrastructure.Repository.ApiInterface
import fr.jbouffard.japan2020.Infrastructure.Repository.HttpClient
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

/**
 * Created by julienb on 20/03/18.
 */
class FlightRequestPresenter(
        private val httpClient: HttpClient,
        private val db: AppDatabase
) {

    suspend fun requestFlightPrice(flightRequest: FlightRequestCommand): List<FlightOffer>  {
        val retrofit = httpClient.retrofit.baseUrl(ApiInterface.BASE_URL).build()

        val service = retrofit.create<ApiInterface>(ApiInterface::class.java)
        val request = FlightRequest(
                flightRequest.origin,
                flightRequest.destination,
                flightRequest.goingDate.toString("y-M-d H:m"),
                flightRequest.returnDate.toString("y-M-d H:m")
        )
        return service.getFlightOffers(request).await()
    }

    @Throws(DomainException::class)
    fun selectRoundTrip(flightOffer: FlightOffer): Holiday {
        val adapter = FlightOfferAdapter()
        val holiday = Holiday(UUID.randomUUID())
        holiday.selectRoundTrip(
                adapter.toFlightPlan(flightOffer.goingFlight),
                adapter.toFlightPlan(flightOffer.returnFlight),
                flightOffer.totalRatePerAdult
        )
        holiday.getUncommittedChanges()
                .filter { it is SelectFlightPlan }
                .map{ buildFlightBudgetProjection(it as SelectFlightPlan) }

        return holiday
    }

    private fun buildFlightBudgetProjection(event: SelectFlightPlan) {
        GlobalScope.launch {
            val budgetFlightEntry = Budget(
                    event.streamId,
                    event.goingFlightPlan.flightPlan.last().arrivalDate.millis,
                    Budget.SERVICE_FLIGHT,
                    event.fare,
                    "Vols aller/retour"
            )
            db.budgetDao().insertOne(budgetFlightEntry)
        }
    }
}