package net.rolodophone.core

import android.graphics.Paint
import android.graphics.RectF

open class ButtonText(private val text: String, private val align: Paint.Align, private val textSize: Float, dim: RectF, dimExceptions: List<RectF> = listOf(), onClick: () -> Unit)
    : Button(dim, dimExceptions, onClick) {

    private val x = when (align) {
        Paint.Align.LEFT -> dim.left
        Paint.Align.RIGHT -> dim.right
        Paint.Align.CENTER -> (dim.left + dim.right) / 2
    }

    override fun draw() {
        super.draw()

        paint.textAlign = align
        paint.textSize = textSize
        canvas.drawText(text, x, dim.centerY() + textSize/2 - w(
            3
        ), paint
        )
    }
}