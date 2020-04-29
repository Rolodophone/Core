package net.rolodophone.core

import android.media.AudioManager
import android.media.SoundPool

class Sounds(private val ctx: MainActivityCore) {
    @Suppress("DEPRECATION")
    private val soundPool: SoundPool =
        SoundPool(10, AudioManager.STREAM_MUSIC, 0)

    fun load(id: Int): Int {
        return soundPool.load(ctx, id, 1)
    }

    fun play(sound: Int) {
        soundPool.play(sound, 1f, 1f, 1, 0, 1f)
    }

    fun pause() = soundPool.autoPause()
    fun resume() = soundPool.autoResume()
}