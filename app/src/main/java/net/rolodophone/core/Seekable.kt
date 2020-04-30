package net.rolodophone.core

import android.graphics.RectF
import androidx.annotation.CallSuper

open class Seekable(val dim: RectF, private val dimExceptions: List<RectF> = listOf(), private val onSeek: (Float, Float) -> Unit, private val onStopSeek: () -> Unit,
                    private val onFling: (Float, Float) -> Unit) {

    private var drawnLastFrame = false
    private var visible = false

    private var seeking = false

    fun update() {
        visible = drawnLastFrame
        drawnLastFrame = false
    }

    @CallSuper
    open fun draw() {
        drawnLastFrame = true
    }

    fun handleStartingSeek(x: Float, y: Float): Boolean {
        if (visible && dim.contains(x, y) && dimExceptions.none { it.contains(x, y) }) {
            seeking = true
            return true
        }

        return false
    }

    fun handleSeek(x: Float, y: Float): Boolean {
        if (seeking) {
            onSeek(x, y)
            return true
        }

        return false
    }

    fun handleStoppingSeek(): Boolean {
        if (seeking) {
            seeking = false
            onStopSeek()
            return true
        }

        return false
    }

    fun handleFling(vx: Float, vy: Float): Boolean {
        if (seeking) {
            seeking = false
            onFling(vx, vy)
            return true
        }

        return false
    }
}