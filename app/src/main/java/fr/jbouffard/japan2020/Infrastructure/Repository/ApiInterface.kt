package fr.jbouffard.japan2020.Infrastructure.Repository

import fr.jbouffard.japan2020.Infrastructure.DTO.*
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

    @POST("flights/offers")
    fun getFlightOffers(@Body flightRequest: FlightRequest): Deferred<List<FlightOffer>>

    @POST("overnight/offers")
    fun getOvernightOffers(@Body overnightRequest: OvernightRequest): Deferred<List<OvernightOffer>>

    @GET("visits")
    fun getVisitsInfo(): Deferred<List<Visit>>
}