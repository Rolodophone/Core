package net.rolodophone.core

import android.media.AudioManager
import android.media.SoundPool

class Sounds(private val ctx: MainActivityCore) {
    @Suppress("DEPRECATION")
    private val soundPool: SoundPool =
        SoundPool(10, AudioManager.STREAM_MUSIC, 0)

    fun load(id: Int): Int = soundPool.load(ctx, id, 1)

    fun play(sound: Int, loop: Boolean = false) = soundPool.play(sound, 1f, 1f, 1, if (loop) 1 else 0, 1f)

    fun pause(sound: Int) = soundPool.pause(sound)
    fun resume(sound: Int) = soundPool.resume(sound)

    fun pauseAll() = soundPool.autoPause()
    fun resumeAll() = soundPool.autoResume()
}