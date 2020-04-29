package net.rolodophone.core

import android.graphics.Bitmap
import android.graphics.RectF

open class ButtonBitmap(private val bitmap: Bitmap, dim: RectF, dimExceptions: List<RectF> = listOf(), onClick: () -> Unit)
    : Button(dim, dimExceptions, onClick) {

    override fun draw() {
        super.draw()
        canvas.drawBitmap(bitmap, null, dim, bitmapPaint)
    }
}