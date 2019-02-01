package fr.jbouffard.japan2020.Infrastructure

import android.arch.persistence.room.Room
import fr.jbouffard.japan2020.Domain.EventStore
import fr.jbouffard.japan2020.Domain.RepositoryInterface
import fr.jbouffard.japan2020.Infrastructure.Adapter.DetailDayAdapter
import fr.jbouffard.japan2020.Infrastructure.Adapter.FlightAdapter
import fr.jbouffard.japan2020.Infrastructure.LocalPersistence.AppDatabase
import fr.jbouffard.japan2020.Infrastructure.Repository.EventStoreImpl
import fr.jbouffard.japan2020.Infrastructure.Repository.HttpClient
import fr.jbouffard.japan2020.Infrastructure.Repository.Repository
import fr.jbouffard.japan2020.presenter.*
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext

/**
 * Created by julienb on 24/05/18.
 */
val japan2020Module: Module =  applicationContext {
    factory { FlightRequestPresenter(get(), get()) }
    factory { VisitRequestPresenter(get(), get()) }
    factory { OvernightRequestPresenter(get(), get()) }
    factory { BudgetPresenter(get()) }
    factory { DetailPlanPresenter(get(), get()) }
    factory { HttpClient() }
    factory { Repository(get()) as RepositoryInterface }
    factory { EventStoreImpl(get()) as EventStore }
    factory { DetailDayAdapter() }
    factory { FlightAdapter() }
    factory { UpdateHolidayStatPresenter(get()) }
    //factory { ResetOnGoingBudget(get()) }
    bean { Room.databaseBuilder(androidApplication(), AppDatabase::class.java, "app").build()}
    //bean {get<AppDatabase>().budgetDao()}
}