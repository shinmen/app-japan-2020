package fr.jbouffard.japan2020.Infrastructure.Repository

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import fr.jbouffard.japan2020.Infrastructure.Adapter.DateTimeTypeAdapter
import fr.jbouffard.japan2020.Infrastructure.Adapter.EventDescriptorAdapter
import fr.jbouffard.japan2020.Infrastructure.Adapter.VisitAdapter
import fr.jbouffard.japan2020.Infrastructure.DTO.Visit
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.joda.time.DateTime
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Created by julienb on 20/03/18.
 */
class HttpClient {

    val retrofit: Retrofit.Builder

    companion object {
        val TAG = HttpClient::class.java.simpleName
    }

    init {
        val cacheSize = 10 * 1024 * 1024 // 10 MB
        val cache = Cache(File("/data/data"), cacheSize.toLong())
        val logging = HttpLoggingInterceptor()
        // set your desired log level
        logging.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()
        httpClient
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .cache(cache)

        val gson = GsonBuilder()
                .registerTypeAdapter(object : TypeToken<DateTime>() {}.type, DateTimeTypeAdapter())
                .registerTypeAdapter(object : TypeToken<Visit>() {}.type, VisitAdapter())
                .registerTypeAdapter(object : TypeToken<EventDescription>() {}.type, EventDescriptorAdapter())
                .create()
        retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(httpClient.build())
    }
}