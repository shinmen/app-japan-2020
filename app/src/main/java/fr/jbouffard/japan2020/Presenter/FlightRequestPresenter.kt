package fr.jbouffard.japan2020.Presenter

import fr.jbouffard.japan2020.Domain.Budget.Entity.BudgetOrganisation
import fr.jbouffard.japan2020.Domain.DomainException
import fr.jbouffard.japan2020.Domain.Travel.Entity.Holiday
import fr.jbouffard.japan2020.Infrastructure.Adapter.FlightOfferAdapter
import fr.jbouffard.japan2020.Infrastructure.Command.FlightRequestCommand
import fr.jbouffard.japan2020.Infrastructure.DTO.FlightOffer
import fr.jbouffard.japan2020.Infrastructure.DTO.FlightRequest
import fr.jbouffard.japan2020.Infrastructure.Repository.ApiInterface
import fr.jbouffard.japan2020.Infrastructure.Repository.HttpClient
import retrofit2.Retrofit
import java.util.*

/**
 * Created by julienb on 20/03/18.
 */
class FlightRequestPresenter(private val httpClient: HttpClient) {

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
    fun selectRoundTrip(flightOffer: FlightOffer) {
        val adapter = FlightOfferAdapter(flightOffer)
        val holiday = Holiday(UUID.randomUUID())
        holiday.selectRoundTrip(adapter.toTravelDomain())

        val budget = BudgetOrganisation(UUID.randomUUID())
        budget.provisionRoundTrip(adapter.toBudgetDomain())
    }
}