package fr.jbouffard.japan2020.View.PlanHoliday

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import fr.jbouffard.japan2020.R
import kotlinx.android.synthetic.main.activity_main.*
import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.AnimationUtils
import fr.jbouffard.japan2020.View.PlanHoliday.FlightRequestActivity
import org.jetbrains.anko.sdk25.coroutines.onClick


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        line.post{
            plane.post {
                val animation = ObjectAnimator.ofFloat(plane, "translationX", line.width.toFloat() - plane.width.toFloat())
                animation.duration = 1500
                animation.start()
                welcome.visibility = View.VISIBLE
                val welcomeAnimation = AnimationUtils.loadAnimation(this@MainActivity, android.R.anim.fade_in)
                welcomeAnimation.duration = 3000
                welcome.startAnimation(welcomeAnimation)
            }
        }

        new_holiday_button.onClick {
            val i = FlightRequestActivity.newIntent(this@MainActivity)
            startActivity(i)
        }
/*
        val logging = HttpLoggingInterceptor()
        // set your desired log level
        logging.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)

        val gson = GsonBuilder()
                .registerTypeAdapter(EventDescription::class.java, EventDescriptorAdapter())
                .create()
        val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
        val api = retrofit.baseUrl(ApiInterface.BASE_URL).build()
        val repoEvents = api.create(ApiInterface::class.java)

        launch(UI) {
            try {
                val events = repoEvents.getHistory().await()
                events
            } catch (ex: Exception) {
                Log.d("error", ex.stackTrace.toString())
            }
        }*/
    }


}
