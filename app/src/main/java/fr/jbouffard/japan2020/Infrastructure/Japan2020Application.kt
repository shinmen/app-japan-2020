package fr.jbouffard.japan2020.Infrastructure

import android.app.Application
import fr.jbouffard.japan2020.Infrastructure.Repository.HttpClient
import fr.jbouffard.japan2020.Presenter.FlightRequestPresenter
import org.koin.android.ext.android.get
import org.koin.android.ext.android.startKoin
import org.koin.dsl.module.Module

/**
 * Created by julienb on 24/05/18.
 */
class Japan2020Application: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(japan2020Module))
    }
}