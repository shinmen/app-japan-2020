package fr.jbouffard.japan2020.Infrastructure.Repository

import fr.jbouffard.japan2020.Infrastructure.DTO.Result
import kotlinx.coroutines.Deferred
import retrofit2.http.*

/**
 * Created by julienb on 04/06/18.
 */
interface EventStoreInterface {
    companion object {
        const val BASE_URL = "https://fierce-oasis-90265.herokuapp.com"
    }

    @POST("events/{streamId}")
    fun newBatch(@Path("streamId") streamId: String, @Body batchEvent: List<EventDescription>): Deferred<Result>

    @GET("events/{streamId}")
    fun getHistory(@Path("streamId") streamId: String): Deferred<List<EventDescription>>
}