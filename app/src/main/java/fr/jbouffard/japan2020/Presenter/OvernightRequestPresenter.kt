package fr.jbouffard.japan2020.Presenter

import fr.jbouffard.japan2020.Infrastructure.Command.FlightRequestCommand
import fr.jbouffard.japan2020.Infrastructure.DTO.FlightOffer
import fr.jbouffard.japan2020.Infrastructure.DTO.FlightRequest
import fr.jbouffard.japan2020.Infrastructure.DTO.OvernightOffer
import fr.jbouffard.japan2020.Infrastructure.DTO.OvernightRequest
import fr.jbouffard.japan2020.Infrastructure.Repository.ApiInterface
import fr.jbouffard.japan2020.Infrastructure.Repository.HttpClient

/**
 * Created by julienb on 31/05/18.
 */
class OvernightRequestPresenter(private val httpClient: HttpClient) {

    suspend fun requestOvernightsOffers(): List<OvernightOffer>  {
        val retrofit = httpClient.retrofit.baseUrl(ApiInterface.BASE_URL).build()

        val service = retrofit.create<ApiInterface>(ApiInterface::class.java)
        val request = OvernightRequest(
                "2018-11-02 12:00",
                "2018-11-10 12:00",
                6,
                "Tokyo"
        )
        return service.getOvernightOffers(request).await()
    }
}