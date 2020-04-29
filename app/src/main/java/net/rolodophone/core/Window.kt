package net.rolodophone.core

abstract class Window {
    abstract val ctx: MainActivityCore
    abstract val downButtons: MutableList<Button>
    abstract val upButtons: MutableList<Button>

    open fun update() {}
    open fun draw() {}
    open fun pause() {}
    open fun resume() {}
}