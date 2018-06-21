package fr.jbouffard.japan2020.Infrastructure.Utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.VectorDrawable
import fr.jbouffard.japan2020.R

/**
 * Created by julienb on 21/06/18.
 */
class VectorDrawableTransformer {
    companion object {
        fun toBitmap(vector: VectorDrawable): Bitmap {
            val bitmap = Bitmap.createBitmap(vector.intrinsicWidth, vector.intrinsicHeight, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            vector.apply{
                setBounds(0, 0, canvas.width, canvas.height)
                draw(canvas)
            }

            return bitmap
        }
    }
}