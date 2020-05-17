package net.rolodophone.core

abstract class LoadableWindow(ctx: MainActivityCore, loadingWindow: LoadingWindow) : Window(ctx) {
    open fun onLoad() {}
}