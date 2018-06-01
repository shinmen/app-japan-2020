package fr.jbouffard.japan2020.Infrastructure.DTO

/**
 * Created by julienb on 31/05/18.
 */
data class OvernightRequest(
        val checkinDate: String,
        val checkoutDate: String,
        val guestsNb: Int,
        val city: String
)