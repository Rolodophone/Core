package net.rolodophone.core

abstract class Window(val ctx: MainActivityCore) {

    open val downButtons = mutableListOf<Button>()
    open val upButtons = mutableListOf<Button>()
    open val seekables = listOf<Seekable>()

    open fun update() {}
    open fun draw() {}
    open fun pause() {}
    open fun resume() {}
}