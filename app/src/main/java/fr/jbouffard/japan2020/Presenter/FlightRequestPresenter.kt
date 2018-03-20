package fr.jbouffard.japan2020.Presenter

import fr.jbouffard.japan2020.Domain.Travel.Command.FlightRequestCommand
import fr.jbouffard.japan2020.Infrastructure.DTO.FlightOffer
import fr.jbouffard.japan2020.Infrastructure.DTO.FlightRequest
import fr.jbouffard.japan2020.Infrastructure.Repository.ApiInterface
import fr.jbouffard.japan2020.Infrastructure.Repository.HttpClient

/**
 * Created by julienb on 20/03/18.
 */
class FlightRequestPresenter {

    suspend fun requestFlightPrice(flightRequest: FlightRequestCommand): List<FlightOffer>  {
        val retrofit = HttpClient().retrofit.baseUrl(ApiInterface.BASE_URL).build()

        val service = retrofit.create<ApiInterface>(ApiInterface::class.java)
        val request = FlightRequest(
            flightRequest.origin,
            flightRequest.destination,
                "2018-11-02 12:00",
                "2018-11-10 12:00"
            /*flightRequest.goingDate.toString("y-M-d H:m"),
            flightRequest.returnDate.toString("y-M-d H:m")*/
        )
        return service.getFlightOffers(request).await()
    }
}