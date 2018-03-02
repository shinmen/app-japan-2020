package fr.jbouffard.japan2020.View.PlanHoliday

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import fr.jbouffard.japan2020.Domain.Travel.Event.StreamEvent
import fr.jbouffard.japan2020.Infrastructure.Adapter.StreamEventAdapter
import fr.jbouffard.japan2020.Infrastructure.Repository.ApiEventStoreInterface
import fr.jbouffard.japan2020.Infrastructure.Repository.ESEvent
import fr.jbouffard.japan2020.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                message.setText(R.string.title_home)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                message.setText(R.string.title_dashboard)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                message.setText(R.string.title_notifications)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        val logging = HttpLoggingInterceptor()
        // set your desired log level
        logging.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)

        val gson = GsonBuilder()
                .setDateFormat("yyyy-M-dd hh:mm:ss")
                .registerTypeAdapter(ESEvent::class.java, StreamEventAdapter())
                .create()
        val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
        val api = retrofit.baseUrl(ApiEventStoreInterface.BASE_URL).build()
        val repoEvents = api.create(ApiEventStoreInterface::class.java)

        launch(UI) {
            try {
                val events = repoEvents.getHistory().await()
                events
            } catch (ex: Exception) {
                Log.d("error", ex.stackTrace.toString())
            }
        }
    }
}
