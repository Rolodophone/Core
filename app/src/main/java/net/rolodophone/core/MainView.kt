package net.rolodophone.core

import android.annotation.SuppressLint
import android.os.SystemClock
import android.view.SurfaceView

@SuppressLint("ViewConstructor")
internal class MainView(private val ctx: MainActivityCore) : SurfaceView(ctx), Runnable {

    override fun run() {
        while (appOpen) {
            val initialTime = SystemClock.elapsedRealtime()

            if (holder.surface.isValid) {
                val activeWindowForFrame = ctx.activeWindow

                //update
                activeWindowForFrame.update()

                //draw
                val c = holder.lockCanvas()
                if (c != null) {
                    canvas = c

                    activeWindowForFrame.draw()

                    holder.unlockCanvasAndPost(canvas)
                }

                //calculate fps
                val timeElapsed = SystemClock.elapsedRealtime() - initialTime
                fps = if (timeElapsed == 0L) 2000f else 1000f / timeElapsed
                if (fps < 20f) fps = 20f //make the game slower if the fps is too low
            }
        }
    }
}