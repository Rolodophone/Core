package net.rolodophone.core

import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSpec
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.RawResourceDataSource
import com.google.android.exoplayer2.util.Util

class Music(ctx: MainActivityCore, appNameId: Int) {
    private val player = SimpleExoPlayer.Builder(ctx).build()

    private val dataSourceFactory = DefaultDataSourceFactory(ctx, Util.getUserAgent(ctx, ctx.resources.getString(appNameId)))
    private val rawDataSource = RawResourceDataSource(ctx)

    var onPrepared = {}


    init {
        player.addListener(object : Player.EventListener {
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                if (playbackState == Player.STATE_READY) {
                    onPrepared()
                }
            }
        })
    }


    fun load(id: Int): MediaSource {
        rawDataSource.open(
            DataSpec(
                RawResourceDataSource.buildRawResourceUri(
                    id
                )
            )
        )
        return ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(rawDataSource.uri)
    }


    fun prepare(mediaSource: MediaSource) {
        player.prepare(mediaSource)
        player.playWhenReady = false
    }


    fun pause() { player.playWhenReady = false }
    fun resume() { player.playWhenReady = true }
}