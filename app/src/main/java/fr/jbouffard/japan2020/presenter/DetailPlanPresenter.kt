package fr.jbouffard.japan2020.presenter

import android.support.v4.util.SparseArrayCompat
import fr.jbouffard.japan2020.Domain.Travel.Entity.Holiday
import fr.jbouffard.japan2020.Infrastructure.Adapter.DetailDayAdapter
import fr.jbouffard.japan2020.Infrastructure.Adapter.FlightAdapter
import fr.jbouffard.japan2020.View.PlanHoliday.*
import fr.jbouffard.japan2020.View.PlanHoliday.DetailPlan.*

class DetailPlanPresenter(private val dayAdapter: DetailDayAdapter, private val flightAdapter: FlightAdapter) {
    fun getDetailDays(holiday: Holiday): List<ViewType> {
        val airTransportation = holiday.getAirTransportation()
        val goingFlights = flightAdapter.toDetailFlights(
                airTransportation.goingFlightPlan.flightPlan.first().departureDate,
                airTransportation.goingFlightPlan.flightPlan,
                airTransportation.goingFlightPlan.companyName,
                airTransportation.fare
        )
        val returnFlights = flightAdapter.toDetailFlights(
                airTransportation.returnFlightPlan.flightPlan.first().departureDate,
                airTransportation.returnFlightPlan.flightPlan,
                airTransportation.returnFlightPlan.companyName,
                null
                )
        val days = dayAdapter.toDetailDays(holiday.getPlannedDays())

        val railPassDay = dayAdapter.toRailpassDay(holiday)

        return listOf(goingFlights, days, railPassDay, returnFlights)
                .flatten()
                .groupBy { it.getDate() }
                .flatMap {
                    val lines = it.value.toMutableList()
                    lines.add(0, DateDetailDay(it.key))
                    lines.add(DetailDaySeparator())
                    lines
                }
                .toList()
                .plus(DetailTotalPrice(
                        holiday.getBudget(),
                        holiday.getAirTransportation().returnFlightPlan.flightPlan.last().arrivalDate)
                )
    }

    fun getAdapters(): SparseArrayCompat<DetailDayRecyclerViewAdapter.ViewTypeDelegateAdapter> {
        val delegates = SparseArrayCompat<DetailDayRecyclerViewAdapter.ViewTypeDelegateAdapter>(3)
        delegates.append(DetailFlightSchedule.VIEW_TYPE, FlightDelegateAdapter())
        delegates.append(DetailDaySeparator.VIEW_TYPE, SeparatorDelegateAdapter())
        delegates.append(OvernightDaySchedule.VIEW_TYPE, OvernightDelegateAdapter())
        delegates.append(VisitDaySchedule.VIEW_TYPE, VisitDelegateAdapter())
        delegates.append(DateDetailDay.VIEW_TYPE, DateDayDelegateAdapter())
        delegates.append(DetailRailpassPackage.VIEW_TYPE, DetailRailpassDelegateAdapter())
        delegates.append(DetailTotalPrice.VIEW_TYPE, DetailTotalPriceDelegateAdapter())

        return delegates
    }
}