package fr.jbouffard.japan2020

import fr.jbouffard.japan2020.Domain.Travel.Entity.Holiday
import fr.jbouffard.japan2020.Domain.Travel.Event.FlightPlanSelected
import fr.jbouffard.japan2020.Domain.Travel.Exception.HolidayTooExpensiveException
import fr.jbouffard.japan2020.Domain.Travel.Exception.NotEnoughTimeToPlanException
import fr.jbouffard.japan2020.Domain.Travel.ValueObject.City
import fr.jbouffard.japan2020.Domain.Travel.ValueObject.Flight
import org.hamcrest.CoreMatchers.instanceOf
import org.joda.time.DateTime
import org.joda.time.Period
import org.junit.Assert.*
import org.junit.Test
import java.util.*

/**
 * Created by julienb on 27/02/18.
 */
class FlightPlanTest {

    @Test(expected = NotEnoughTimeToPlanException::class)
    fun departure_too_close() {
        val city = City("Paris", "France")
        val goingFlight = Flight(700, city, city, "Air", Date(), Date(),12.toFloat(), 700.toFloat(), false, 0)
        val returnFlight = Flight(700, city, city, "Air", Date(), Date(),12.toFloat(), 700.toFloat(), false, 0)
        val holiday = Holiday()
        holiday.selectRoundTrip(goingFlight, returnFlight)
    }

    @Test(expected = HolidayTooExpensiveException::class)
    fun holiday_too_long() {
        val city = City("Paris", "France")
        val oneMonthAgo = DateTime.now().minus(Period.days(30))
        val goingFlight = Flight(700, city, city, "Air", oneMonthAgo.toDate(), Date(),12.toFloat(), 700.toFloat(), false, 0)
        val returnFlight = Flight(700, city, city, "Air", Date(), Date(),12.toFloat(), 700.toFloat(), false, 0)
        val holiday = Holiday()
        holiday.selectRoundTrip(goingFlight, returnFlight)
    }

    @Test
    fun flight_selected() {
        val city = City("Paris", "France")
        val oneMonthAgo = DateTime.now().minus(Period.days(30))
        val twentyDaysAgo = DateTime.now().minus(Period.days(20))
        val goingFlight = Flight(700, city, city, "Air", oneMonthAgo.toDate(), oneMonthAgo.toDate(), 12.toFloat(), 700.toFloat(), false, 0)
        val returnFlight = Flight(700, city, city, "Air", twentyDaysAgo.toDate(), twentyDaysAgo.toDate(), 12.toFloat(), 700.toFloat(), false, 0)
        val holiday = Holiday()
        holiday.selectRoundTrip(goingFlight, returnFlight)
        val changes = holiday.getUncommittedChanges()

        assertSame(changes.size, 1)
        assertThat(changes.first(), instanceOf(FlightPlanSelected::class.java))
    }


}