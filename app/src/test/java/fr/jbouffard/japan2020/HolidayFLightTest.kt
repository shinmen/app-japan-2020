package fr.jbouffard.japan2020

import fr.jbouffard.japan2020.Domain.Travel.Entity.Holiday
import fr.jbouffard.japan2020.Domain.Travel.ValueObject.City
import fr.jbouffard.japan2020.Domain.Travel.ValueObject.Flight
import org.junit.Assert
import org.junit.Test
import java.util.*

/**
 * Created by julienb on 27/02/18.
 */
class HolidayFLightTest {

    @Test(expected = Exception::class)
    fun departure_too_short() {
        val city = City("Paris", "France")
        val goingFlight = Flight(700, city, city, "Air", Date(), Date(),12.toFloat(), 700.toFloat(), false, 0)
        val returnFlight = Flight(700, city, city, "Air", Date(), Date(),12.toFloat(), 700.toFloat(), false, 0)

        val holiday = Holiday()

        holiday.selectRoundTrip(goingFlight, returnFlight)
        holiday.getUncommittedChanges()
    }
}