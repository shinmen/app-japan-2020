package fr.jbouffard.japan2020.Infrastructure.Utils

import android.content.Context
import android.content.res.Resources
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.view.View
import fr.jbouffard.japan2020.R
import org.jetbrains.anko.backgroundColor

/**
 * Created by julienb on 29/06/18.
 */
class SnackBarStyler(private val context: Context) {
    companion object {
        const val BACKGROUND_STYLE_ERROR = "error"
        const val BACKGROUND_STYLE_INFO = "info"
    }

    fun errorSnack(view: View, mess: String) {
        val snackbar = Snackbar.make(view, mess, Snackbar.LENGTH_SHORT)
        snackbar.view.backgroundColor = ContextCompat.getColor(context, R.color.colorPrimaryDark)
        snackbar.show()
    }

    fun infoSnack(view: View, mess: String) {
        val snackbar = Snackbar.make(view, mess, Snackbar.LENGTH_SHORT)
        snackbar.view.backgroundColor = ContextCompat.getColor(context, android.R.color.holo_blue_light)
        snackbar.show()
    }
}