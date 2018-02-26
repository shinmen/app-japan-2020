package fr.jbouffard.japan2020.Domain.Travel.Event

/**
 * Created by julienb on 26/02/18.
 */
sealed class AppEvent
var version: Int = 0
data class Test(var version: Int): AppEvent()
data class Test2(var version: Int): AppEvent()