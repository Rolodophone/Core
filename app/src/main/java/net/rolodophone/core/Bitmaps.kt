package net.rolodophone.core

import android.graphics.Bitmap
import android.graphics.BitmapFactory

class Bitmaps(private val ctx: MainActivityCore) {
    private val bitmapOptions = BitmapFactory.Options()
    init {
        bitmapOptions.inScaled = false
    }

    fun load(id: Int): Bitmap {
        return BitmapFactory.decodeResource(ctx.resources, id, bitmapOptions)
    }
}