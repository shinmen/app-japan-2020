package fr.jbouffard.japan2020.Infrastructure

import fr.jbouffard.japan2020.Infrastructure.Repository.HttpClient
import fr.jbouffard.japan2020.Presenter.FlightRequestPresenter
import fr.jbouffard.japan2020.Presenter.OvernightRequestPresenter
import fr.jbouffard.japan2020.Presenter.VisitRequestPresenter
import org.koin.android.ext.android.get
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext

/**
 * Created by julienb on 24/05/18.
 */
val japan2020Module: Module =  applicationContext {
    factory { FlightRequestPresenter(get()) }
    factory { OvernightRequestPresenter(get()) }
    factory { VisitRequestPresenter(get()) }
    factory { HttpClient() }
}