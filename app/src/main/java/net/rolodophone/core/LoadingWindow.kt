package net.rolodophone.core

abstract class LoadingWindow(ctx: MainActivityCore, private val nextWindowConstructor: (LoadingWindow) -> LoadableWindow, private var count: Int)
: Window(ctx) {
    private lateinit var nextWindow: LoadableWindow
    private val loadingInitThread: Thread

    init {
        loadingInitThread = Thread({
            nextWindow = nextWindowConstructor(this)
            if (count <= 0) finishLoading()
        },
            "Loading init thread"
        )

        loadingInitThread.start()
    }

    fun countDown() {
        count--
        if (count <= 0 && !loadingInitThread.isAlive) finishLoading()
    }

    private fun finishLoading() {
        ctx.activeWindow = nextWindow
        nextWindow.onLoad()
    }
}