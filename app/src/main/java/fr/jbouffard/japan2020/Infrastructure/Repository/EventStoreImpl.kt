package fr.jbouffard.japan2020.Infrastructure.Repository

import fr.jbouffard.japan2020.Domain.DomainEvent
import fr.jbouffard.japan2020.Domain.EventStore
import java.util.*

/**
 * Created by julienb on 28/02/18.
 */
class EventStoreImpl: EventStore {
    override fun saveEvents(uuid: UUID, changes: List<DomainEvent>, expectedVersion: Int) {

    }

    override fun getAggregateHistory(uuid: UUID): List<DomainEvent> {
        return emptyList()
    }
}