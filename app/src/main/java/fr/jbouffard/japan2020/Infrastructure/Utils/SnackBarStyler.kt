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

    fun errorSnackWithRetry(view: View, mess: String, listener: (View) -> Unit) {
        val snackbar = Snackbar.make(view, mess, Snackbar.LENGTH_LONG)
        snackbar.view.backgroundColor = ContextCompat.getColor(context, R.color.colorPrimaryDark)
        snackbar.setAction("Nouvel essai", listener)
        snackbar.show()
    }
}