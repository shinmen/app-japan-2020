package fr.jbouffard.japan2020.Infrastructure.Repository

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
    fun getFlightOffers(@Body flightRequest: FlightRequest)

    /*@GET("eventlv/list")
    fun getComingEvents(): Call<List<EventLV>>

    @GET("eventlv/list/{category}")
    fun getFilteredEvents(@Path("category") category: String): Call<List<EventLV>>

    @POST("eventlv/create")
    fun saveEvent(@Body event:EventLV): Call<EventLV>

    @GET("eventlv/category/list")
    fun getCategories(): Call<List<Category>>

    @POST("eventlv/subscribe/{uuid}/{username}")
    fun subscribeEvent(@Path("uuid") uuid: String, @Path("username") username: String): Call<Any>

    @POST("eventlv/unsubscribe/{uuid}/{username}")
    fun unSubscribeEvent(@Path("uuid") uuid: String, @Path("username") username: String): Call<Any>*/
}