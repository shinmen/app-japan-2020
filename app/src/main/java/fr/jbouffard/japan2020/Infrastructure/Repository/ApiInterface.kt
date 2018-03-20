package fr.jbouffard.japan2020.Infrastructure.Repository

import fr.jbouffard.japan2020.Infrastructure.DTO.FlightOffer
import fr.jbouffard.japan2020.Infrastructure.DTO.FlightRequest
import kotlinx.coroutines.experimental.Deferred
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Created by julienb on 01/03/18.
 */
interface ApiInterface {

    companion object {
        const val BASE_URL = "http://www.japon2020.jbouffard.fr/"
        //const val BASE_URL = "http://www.mocky.io"
    }
    @GET("/v2/5a9d3f4c3100005700ab5330")
    fun getHistory(): Deferred<List<EventDescription>>

    @POST("flights/offers")
    fun getFlightOffers(@Body flightRequest: FlightRequest): Deferred<List<FlightOffer>>
}