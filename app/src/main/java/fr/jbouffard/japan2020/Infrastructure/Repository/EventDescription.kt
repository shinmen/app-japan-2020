package fr.jbouffard.japan2020.Infrastructure.Repository


import fr.jbouffard.japan2020.Domain.Travel.Event.Event
import java.util.*

/**
 * Created by julienb on 01/03/18.
 */
data class EventDescription(val eventId: UUID, val eventType: String, val data: Event)