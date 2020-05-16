package net.rolodophone.core

import android.media.AudioManager
import android.media.SoundPool

class Sounds(private val ctx: MainActivityCore) {
    @Suppress("DEPRECATION")
    private val soundPool: SoundPool =
        SoundPool(10, AudioManager.STREAM_MUSIC, 0)

    fun load(resId: Int): Int = soundPool.load(ctx, resId, 1)

    fun play(soundID: Int, loop: Boolean = false) = soundPool.play(soundID, 1f, 1f, 1, if (loop) 1 else 0, 1f)

    fun pause(streamID: Int) = soundPool.pause(streamID)
    fun resume(steamID: Int) = soundPool.resume(steamID)

    fun pauseAll() = soundPool.autoPause()
    fun resumeAll() = soundPool.autoResume()
}