package fr.jbouffard.japan2020.Domain

import fr.jbouffard.japan2020.Domain.Travel.Event.AppEvent
import java.util.*

/**
 * Created by julienb on 26/02/18.
 */
abstract class AggregateRoot {
    lateinit var uuid: UUID
    private set

    protected var version: Int = 0
    private var changes: MutableList<AppEvent> = mutableListOf()

    fun getUncommittedChanges(): List<AppEvent> {
        return changes
    }

    fun markChangesAsCommited() {
        changes = mutableListOf()
    }

    fun replayHistory(history: List<AppEvent>) {
        history.forEach {
            applyEvent(it, false)
        }
    }

    protected fun applyChange(event: AppEvent) {
        applyEvent(event, true)
    }

    protected open fun applyEvent(event: AppEvent, isNew: Boolean) {
        if (isNew) {
            changes.add(event)
        }
    }


}
