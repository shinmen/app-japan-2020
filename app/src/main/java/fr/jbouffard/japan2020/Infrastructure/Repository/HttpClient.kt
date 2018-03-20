package fr.jbouffard.japan2020.Infrastructure.Repository

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by julienb on 20/03/18.
 */
class HttpClient {

    val retrofit: Retrofit.Builder

    companion object {
        val TAG = HttpClient::class.java.simpleName
    }

    init {
        val logging = HttpLoggingInterceptor()
        // set your desired log level
        logging.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)

        val gson = GsonBuilder().setDateFormat("y-M-d H:m").create()
        retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(httpClient.build())
    }
}