package fr.jbouffard.japan2020.Infrastructure.Repository

import fr.jbouffard.japan2020.Domain.AggregateRoot
import fr.jbouffard.japan2020.Domain.EventStore
import fr.jbouffard.japan2020.Domain.RepositoryInterface
import java.util.*

/**
 * Created by julienb on 26/02/18.
 */
class Repository(private val eventStore:EventStore): RepositoryInterface {
    override fun save(entity: AggregateRoot, expectedVersion: Int) {
        eventStore.saveEvents(entity.uuid, entity.getUncommittedChanges(), expectedVersion)
    }

    override fun getById(uuid: UUID, entity: AggregateRoot): AggregateRoot {
        val history = eventStore.getAggregateHistory(uuid)
        entity.replayHistory(history)

        return entity
    }
}