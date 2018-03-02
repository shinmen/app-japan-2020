package fr.jbouffard.japan2020.Domain

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by julienb on 02/03/18.
 */
class Test {
    @SerializedName("username")
    @Expose
    var username: String

    constructor(username: String) {
        this.username = username
    }

}