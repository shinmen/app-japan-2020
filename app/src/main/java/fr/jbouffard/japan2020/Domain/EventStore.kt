package fr.jbouffard.japan2020.Domain

import fr.jbouffard.japan2020.Domain.Travel.Event.StreamEvent
import java.util.*

/**
 * Created by julienb on 26/02/18.
 */
interface EventStore {
    fun saveEvents(uuid: UUID, changes: List<StreamEvent>, expectedVersion: Int)
    fun getAggregateHistory(uuid: UUID) :List<StreamEvent>
}