package fr.jbouffard.japan2020.Presenter

import fr.jbouffard.japan2020.Domain.Travel.Entity.Holiday
import fr.jbouffard.japan2020.Domain.Travel.ValueObject.AccommodationAddress
import fr.jbouffard.japan2020.Domain.Travel.ValueObject.City
import fr.jbouffard.japan2020.Infrastructure.DTO.*
import fr.jbouffard.japan2020.Infrastructure.Repository.ApiInterface
import fr.jbouffard.japan2020.Infrastructure.Repository.HttpClient
import org.joda.time.DateTime
import org.joda.time.Period

/**
 * Created by julienb on 31/05/18.
 */
class DayRequestPresenter(private val httpClient: HttpClient) {

    suspend fun requestVisits(): List<Visit>  {
        val retrofit = httpClient.retrofit.baseUrl(ApiInterface.BASE_URL).build()

        val service = retrofit.create<ApiInterface>(ApiInterface::class.java)
        return service.getVisitsInfo().await()
    }

    suspend fun requestOvernightsOffers(date: DateTime, city: City): List<OvernightOffer>  {
        val retrofit = httpClient.retrofit.baseUrl(ApiInterface.BASE_URL).build()

        val service = retrofit.create<ApiInterface>(ApiInterface::class.java)
        val request = OvernightRequest(
                date.toString("y-M-d 12:00"),
                date.plus(Period.days(1)).toString("y-M-d 12:00"),
                6,
                city.name
        )
        return service.getOvernightOffers(request).await()
    }

    fun visitPlace(holiday: Holiday, destination: String) {
        holiday.goToCity(destination)
        holiday.scheduleVisitCity(destination)
    }

    fun sleepIn(holiday: Holiday, overnightOffer: OvernightOffer) {
        val accommodation = AccommodationAddress(
                overnightOffer.accommodation.commercialName,
                City(overnightOffer.accommodation.city)
                )
        holiday.goToCity(accommodation.city.name)
        holiday.scheduleStayOver(accommodation, overnightOffer.pricePerPax, overnightOffer.weekReduction)
    }

    fun finishDay(holiday: Holiday) {
        holiday.wakeUp()
    }
}