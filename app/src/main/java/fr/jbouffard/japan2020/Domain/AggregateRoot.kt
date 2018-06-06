package fr.jbouffard.japan2020.Domain

import java.util.*

/**
 * Created by julienb on 26/02/18.
 */

abstract class AggregateRoot {
    abstract var uuid: UUID
    abstract val streamId: String

    var version: Int = 0
    protected var changes: MutableList<DomainEvent> = mutableListOf()

    fun getUncommittedChanges(): List<DomainEvent> {
        return changes
    }

    fun markChangesAsCommitted() {
        changes = mutableListOf()
    }

    fun replayHistory(history: List<DomainEvent>) {
        history.forEach {
            applyEvent(it)
        }
    }

    protected open fun applyNewEvent(domainEvent: DomainEvent) {
        changes.add(domainEvent)
    }
    protected abstract fun applyEvent(domainEvent: DomainEvent)
}
