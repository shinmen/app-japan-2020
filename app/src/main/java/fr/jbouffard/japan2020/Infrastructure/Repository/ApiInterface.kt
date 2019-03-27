package fr.jbouffard.japan2020.Infrastructure.Repository

import fr.jbouffard.japan2020.Infrastructure.DTO.*
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Created by julienb on 01/03/18.
 */
interface ApiInterface {

    companion object {
        const val BASE_URL = "https://fierce-oasis-90265.herokuapp.com"
        //const val BASE_URL = "https://www.mocky.io/"
    }

    @POST("flights/offers")
    //@POST("v2/5c4f3b983300002d00c583d9") //short stay
    //@POST("v2/5c2c82bb2e0000070ae875c0")
    fun getFlightOffersAsync(@Body flightRequest: FlightRequest): Deferred<List<FlightOffer>>

    @POST("overnight/offers")
    //@POST("v2/5bf289be2f00009700cfa128")
    fun getOvernightOffersAsync(@Body overnightRequest: OvernightRequest): Deferred<List<OvernightOffer>>

    @GET("visits")
    //@GET("v2/5bf289f02f00009700cfa12b")
    fun getVisitsInfoAsync(): Deferred<List<Visit>>
}