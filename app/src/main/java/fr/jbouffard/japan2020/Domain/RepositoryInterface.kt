package fr.jbouffard.japan2020.Domain

import java.util.*

/**
 * Created by julienb on 26/02/18.
 */
interface RepositoryInterface {
    suspend fun save(entity: AggregateRoot, expectedVersion: Int)
    suspend fun getById(uuid: String, entity: AggregateRoot): AggregateRoot
}