package net.rolodophone.core

import android.graphics.RectF
import androidx.annotation.CallSuper

open class Button(val dim: RectF, private val dimExceptions: List<RectF> = listOf(), private val onClick: () -> Unit) {

    private var drawnLastFrame = false
    private var visible = false

    fun update() {
        visible = drawnLastFrame
        drawnLastFrame = false
    }

    @CallSuper
    open fun draw() {
        drawnLastFrame = true
    }

    fun handleClick(x: Float, y: Float): Boolean {
        if (visible && dim.contains(x, y) && dimExceptions.none { it.contains(x, y) }) {
            onClick()
            return true
        }
        else return false
    }
}