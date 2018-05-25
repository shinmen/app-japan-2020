package fr.jbouffard.japan2020.Domain.Budget.Exception

import fr.jbouffard.japan2020.Domain.DomainException

/**
 * Created by julienb on 28/02/18.
 */
class HolidayTooExpensiveException(message: String): DomainException(message)