package fr.jbouffard.japan2020.Domain

import java.util.*

/**
 * Created by julienb on 21/05/18.
 */
interface DomainEvent {
    val streamId: String
}