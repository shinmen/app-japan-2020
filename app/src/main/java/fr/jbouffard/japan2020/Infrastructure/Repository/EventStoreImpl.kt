package fr.jbouffard.japan2020.Infrastructure.Repository

import fr.jbouffard.japan2020.Domain.DomainEvent
import fr.jbouffard.japan2020.Domain.EventStore
import java.util.*

/**
 * Created by julienb on 28/02/18.
 */
class EventStoreImpl(private val httpClient: HttpClient): EventStore {

    override suspend fun saveEvents(streamId: String, changes: List<DomainEvent>, expectedVersion: Int) {
        val client = httpClient.retrofit.baseUrl(EventStoreInterface.BASE_URL).build()
        val service = client.create<EventStoreInterface>(EventStoreInterface::class.java)
        val events = mutableListOf<EventDescription>()
        changes.forEach {
            events.add(EventDescription(UUID.randomUUID(), it.javaClass.simpleName, it))
        }
        //service.newBatch(streamId, events).await()
    }

    override suspend fun getAggregateHistory(uuid: String): List<DomainEvent> {
        val client = httpClient.retrofit.baseUrl(EventStoreInterface.BASE_URL).build()
        val service = client.create<EventStoreInterface>(EventStoreInterface::class.java)

        return service.getHistory(uuid).await().map { it.data }
    }
}