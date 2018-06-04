package fr.jbouffard.japan2020.Domain

import java.util.*

/**
 * Created by julienb on 26/02/18.
 */
interface EventStore {
    suspend fun saveEvents(uuid: UUID, changes: List<DomainEvent>, expectedVersion: Int)
    suspend fun getAggregateHistory(uuid: String): List<DomainEvent>
}