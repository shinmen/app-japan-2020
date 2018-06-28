package fr.jbouffard.japan2020.Domain.Travel.Exception

import fr.jbouffard.japan2020.Domain.DomainException

/**
 * Created by julienb on 28/06/18.
 */
class RailpassExpiredException(message: String): DomainException(message)