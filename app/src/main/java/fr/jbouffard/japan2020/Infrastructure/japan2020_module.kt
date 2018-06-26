package fr.jbouffard.japan2020.Infrastructure

import fr.jbouffard.japan2020.Domain.EventStore
import fr.jbouffard.japan2020.Domain.RepositoryInterface
import fr.jbouffard.japan2020.Infrastructure.Repository.EventStoreImpl
import fr.jbouffard.japan2020.Infrastructure.Repository.HttpClient
import fr.jbouffard.japan2020.Infrastructure.Repository.Repository
import fr.jbouffard.japan2020.Presenter.FlightRequestPresenter
import fr.jbouffard.japan2020.Presenter.OvernightRequestPresenter
import fr.jbouffard.japan2020.Presenter.DayRequestPresenter
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext

/**
 * Created by julienb on 24/05/18.
 */
val japan2020Module: Module =  applicationContext {
    factory { FlightRequestPresenter(get(), get()) }
    factory { DayRequestPresenter(get()) }
    factory { HttpClient() }
    factory { Repository(get()) as RepositoryInterface }
    factory { EventStoreImpl(get()) as EventStore }
}