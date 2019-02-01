package fr.jbouffard.japan2020.presenter

import fr.jbouffard.japan2020.Domain.EventStore
import fr.jbouffard.japan2020.Domain.Travel.Entity.Holiday

class UpdateHolidayStatPresenter(private val eventStore: EventStore) {

    suspend fun updateHolidayStats(holiday: Holiday) {
        eventStore.saveEvents(holiday.streamId, holiday.getUncommittedChanges(), holiday.getUncommittedChanges().size)
    }
}