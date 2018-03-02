package fr.jbouffard.japan2020.Domain

import fr.jbouffard.japan2020.Domain.Travel.Event.StreamEvent
import java.util.*

/**
 * Created by julienb on 26/02/18.
 */
abstract class AggregateRoot {
    lateinit var uuid: UUID
    private set

    protected var version: Int = 0
    private var changes: MutableList<StreamEvent> = mutableListOf()

    fun getUncommittedChanges(): List<StreamEvent> {
        return changes
    }

    fun markChangesAsCommited() {
        changes = mutableListOf()
    }

    fun replayHistory(history: List<StreamEvent>) {
        history.forEach {
            applyEvent(it, false)
        }
    }

    protected fun applyChange(event: StreamEvent) {
        applyEvent(event, true)
    }

    protected open fun applyEvent(event: StreamEvent, isNew: Boolean) {
        if (isNew) {
            changes.add(event)
        }
    }


}
