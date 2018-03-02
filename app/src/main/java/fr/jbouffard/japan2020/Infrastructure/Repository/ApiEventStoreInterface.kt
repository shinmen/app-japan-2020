package fr.jbouffard.japan2020.Infrastructure.Repository

import fr.jbouffard.japan2020.Domain.Test
import fr.jbouffard.japan2020.Domain.Travel.Entity.FlightPlan
import fr.jbouffard.japan2020.Domain.Travel.Event.FlightPlanSelected
import fr.jbouffard.japan2020.Domain.Travel.Event.StreamEvent
import fr.jbouffard.japan2020.Domain.Travel.ValueObject.Flight
import kotlinx.coroutines.experimental.Deferred
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Created by julienb on 01/03/18.
 */
interface ApiEventStoreInterface {

    companion object {
        //const val BASE_URL = "http://www.japon2020.jbouffard.fr/"
        const val BASE_URL = "http://www.mocky.io"
    }
    //5a9913fc2e000022285534cf full
    // 5a9955bc2e00001031553619 with version
    // 5a9948662e0000f5315535c3 full with version
    // 5a9949e92e0000f5315535d1 without data with version
    ///5a994b842e000063315535dd min
    // 5a9955142e00001031553614 flight
    // 5a9950732e000012315535fe test
    // 5a97f7e33000003d325c1fbe
    @GET("/v2/5a9955bc2e00001031553619")
    fun getHistory(): Deferred<List<ESEvent>>

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