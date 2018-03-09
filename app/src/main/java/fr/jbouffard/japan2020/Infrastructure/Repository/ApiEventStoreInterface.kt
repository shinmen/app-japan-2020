package fr.jbouffard.japan2020.Infrastructure.Repository

import kotlinx.coroutines.experimental.Deferred
import retrofit2.http.GET

/**
 * Created by julienb on 01/03/18.
 */
interface ApiEventStoreInterface {

    companion object {
        //const val BASE_URL = "http://www.japon2020.jbouffard.fr/"
        const val BASE_URL = "http://www.mocky.io"
    }
    @GET("/v2/5a9d3f4c3100005700ab5330")
    fun getHistory(): Deferred<List<EventDescription>>

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