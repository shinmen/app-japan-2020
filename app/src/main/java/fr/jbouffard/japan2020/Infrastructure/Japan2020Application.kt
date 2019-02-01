package fr.jbouffard.japan2020.Infrastructure

import android.app.Application
import org.koin.android.ext.android.startKoin

/**
 * Created by julienb on 24/05/18.
 */
class Japan2020Application: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(japan2020Module))
    }
}