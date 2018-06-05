package fr.jbouffard.japan2020.Infrastructure.Repository

import fr.jbouffard.japan2020.Domain.AggregateRoot
import fr.jbouffard.japan2020.Domain.EventStore
import fr.jbouffard.japan2020.Domain.RepositoryInterface

/**
 * Created by julienb on 26/02/18.
 */
class Repository(private val eventStore:EventStore): RepositoryInterface {
    override suspend fun save(entity: AggregateRoot, expectedVersion: Int) {
        eventStore.saveEvents(entity.streamId, entity.getUncommittedChanges(), expectedVersion)
        entity.markChangesAsCommitted()
    }

    override suspend fun getById(uuid: String, entity: AggregateRoot): AggregateRoot {
        val history = eventStore.getAggregateHistory(uuid)
        entity.replayHistory(history)

        return entity
    }
}