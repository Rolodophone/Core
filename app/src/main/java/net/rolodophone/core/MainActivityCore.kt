package net.rolodophone.core

import android.app.Activity
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.view.GestureDetectorCompat

var width = 0f
var height = 0f

var fps = Float.POSITIVE_INFINITY
var canvas = Canvas()

var paint = Paint()
var bitmapPaint = Paint()

var appOpen = false

open class MainActivityCore(private val appNameId: Int, private val activeWindowConstructor: (ctx: MainActivityCore) -> Window) : Activity() {

    private lateinit var mainView: MainView
    private lateinit var thread: Thread
    private lateinit var gestureDetector: GestureDetectorCompat

    lateinit var music: Music
    lateinit var bitmaps: Bitmaps

    lateinit var activeWindow: Window

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainView = MainView(this)
        setContentView(mainView)
        configWindow()
        mainView.holder.setFormat(PixelFormat.RGB_565)

        //initialize paints
        paint.isAntiAlias = true
        paint.isFilterBitmap = true
        bitmapPaint.isAntiAlias = true
        bitmapPaint.isFilterBitmap = false

        gestureDetector = GestureDetectorCompat(this, MyGestureListener())

        //initialise resources
        music = Music(this, appNameId)
        bitmaps = Bitmaps(this)

        activeWindow = activeWindowConstructor(this)
    }


    override fun onRestart() {
        super.onRestart()
        configWindow()
    }

    private fun configWindow() {
        //configure window
        window.decorView.systemUiVisibility = when {
            Build.VERSION.SDK_INT >= 19 -> View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            Build.VERSION.SDK_INT >= 17 -> View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
            else                        -> View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        }

        val dim = Point()
        windowManager.defaultDisplay.getRealSize(dim)

        width = dim.x.toFloat()
        height = dim.y.toFloat()
    }

    override fun onStart() {
        super.onStart()

        appOpen = true
        thread = Thread(mainView)
        thread.name = "GameThread"
        thread.start()
    }

    override fun onPause() {
        super.onPause()
        activeWindow.pause()
    }

    override fun onResume() {
        super.onResume()
        activeWindow.resume()
    }

    override fun onStop() {
        super.onStop()

        appOpen = false
        thread.join()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (gestureDetector.onTouchEvent(event)) return true

        if (event.action == MotionEvent.ACTION_UP) {
            for (seekable in activeWindow.seekables) if (seekable.handleStoppingSeek()) {
                return true
            }
        }

        if (event.action == MotionEvent.ACTION_MOVE) {
            for (seekable in activeWindow.seekables) if (seekable.handleSeek(event.x, event.y)) {
                return true
            }
        }

        return super.onTouchEvent(event)
    }

    private inner class MyGestureListener : GestureDetector.SimpleOnGestureListener() {
        var skipNextUp = false

        override fun onDown(event: MotionEvent): Boolean {
            for (button in activeWindow.downButtons) if (button.handleClick(event.x, event.y)) {
                skipNextUp = true
                return true
            }

            for (seekable in activeWindow.seekables) if (seekable.handleStartingSeek(event.x, event.y)) {
                return true
            }

            return false
        }

        override fun onSingleTapUp(event: MotionEvent): Boolean {
            if (!skipNextUp) {
                for (button in activeWindow.upButtons) if (button.handleClick(event.x, event.y)) {
                    return true
                }
            }
            else {
                skipNextUp = false
            }

            return false
        }

        override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
            for (seekable in activeWindow.seekables) if (seekable.handleFling(velocityX, velocityY)) {
                return true
            }

            return false
        }
    }
}