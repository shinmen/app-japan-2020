package fr.jbouffard.japan2020.Domain

import java.util.*

/**
 * Created by julienb on 26/02/18.
 */
interface RepositoryInterface {
    fun save(entity: AggregateRoot, expectedVersion: Int)
    fun getById(uuid: UUID, entity: AggregateRoot): AggregateRoot
}