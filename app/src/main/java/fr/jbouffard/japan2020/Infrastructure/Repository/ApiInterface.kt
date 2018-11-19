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
        //const val BASE_URL = "http://www.japon2020.jbouffard.fr/"
        const val BASE_URL = "http://www.mocky.io/"
    }

    //@POST("flights/offers")
    @POST("v2/5bf289792f00005400cfa125")
    fun getFlightOffers(@Body flightRequest: FlightRequest): Deferred<List<FlightOffer>>

    //@POST("overnight/offers")
    @POST("v2/5bf289be2f00009700cfa128")
    fun getOvernightOffers(@Body overnightRequest: OvernightRequest): Deferred<List<OvernightOffer>>

    //@GET("visits")
    @GET("v2/5bf289f02f00009700cfa12b")
    fun getVisitsInfo(): Deferred<List<Visit>>
}