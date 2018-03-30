package fr.jbouffard.japan2020.Infrastructure.Repository

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import fr.jbouffard.japan2020.Infrastructure.Adapter.DateTimeTypeAdapter
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.joda.time.DateTime
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

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
                .addInterceptor(logging)
                .cache(cache)

        val gson = GsonBuilder()
                .registerTypeAdapter(object : TypeToken<DateTime>() {}.type, DateTimeTypeAdapter())
                .create()
        retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(httpClient.build())
    }
}