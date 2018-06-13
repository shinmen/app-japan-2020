package fr.jbouffard.japan2020.Presenter

import fr.jbouffard.japan2020.Domain.Travel.Entity.Holiday
import fr.jbouffard.japan2020.Infrastructure.Command.FlightRequestCommand
import fr.jbouffard.japan2020.Infrastructure.DTO.*
import fr.jbouffard.japan2020.Infrastructure.Repository.ApiInterface
import fr.jbouffard.japan2020.Infrastructure.Repository.HttpClient

/**
 * Created by julienb on 31/05/18.
 */
class VisitRequestPresenter(private val httpClient: HttpClient) {

    suspend fun requestVisits(): List<Visit>  {
        val retrofit = httpClient.retrofit.baseUrl(ApiInterface.BASE_URL).build()

        val service = retrofit.create<ApiInterface>(ApiInterface::class.java)
        return service.getVisitsInfo().await()
    }

    suspend fun visitPlace(holiday: Holiday, city: String , position: Int) {
        val date = holiday.getDateOf(position)
    }
}