package fr.jbouffard.japan2020.Infrastructure.DTO

/**
 * Created by julienb on 21/03/18.
 */
class CityCodeMapper {
    private val cityMappingFr: Map<String, String> by lazy { hashMapOf("Bordeaux" to "BOD", "Paris" to "PAR") }
    private val cityMappingJp: Map<String, String> by lazy { hashMapOf("Tokyo" to "TYO", "Osaka" to "OSA", "Nagoya" to "NGO") }

    fun getCodeByFrenchCity(key: String): String {
        return cityMappingFr[key] ?: "BOD"
    }

    fun getCodeByJapanCity(key: String): String {
        return cityMappingJp[key] ?:"TYO"
    }
}