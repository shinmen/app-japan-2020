package fr.jbouffard.japan2020.Domain

import fr.jbouffard.japan2020.Domain.Travel.Event.Event
import java.util.*

/**
 * Created by julienb on 26/02/18.
 */
abstract class AggregateRoot {
    abstract var uuid: UUID

    protected var version: Int = 0
    private var changes: MutableList<Event> = mutableListOf()

    fun getUncommittedChanges(): List<Event> {
        return changes
    }

    fun markChangesAsCommited() {
        changes = mutableListOf()
    }

    fun replayHistory(history: List<Event>) {
        history.forEach {
            applyEvent(it, false)
        }
    }

    protected fun applyChange(event: Event) {
        applyEvent(event, true)
    }

    protected open fun applyEvent(event: Event, isNew: Boolean) {
        if (isNew) {
            changes.add(event)
        }
    }
}
