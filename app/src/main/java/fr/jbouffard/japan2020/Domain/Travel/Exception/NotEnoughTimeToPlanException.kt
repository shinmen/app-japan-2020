package fr.jbouffard.japan2020.Domain.Travel.Exception

import fr.jbouffard.japan2020.Domain.DomainException

/**
 * Created by julienb on 28/02/18.
 */
class NotEnoughTimeToPlanException(message: String): DomainException(message)