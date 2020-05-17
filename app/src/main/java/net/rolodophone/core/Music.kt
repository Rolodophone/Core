package net.rolodophone.core

import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSpec
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.RawResourceDataSource
import com.google.android.exoplayer2.util.Util

class Music(ctx: MainActivityCore, appNameId: Int) {

    private val dataSourceFactory = DefaultDataSourceFactory(ctx, Util.getUserAgent(ctx, ctx.resources.getString(appNameId)))
    private val rawDataSource = RawResourceDataSource(ctx)

    fun load(id: Int): MediaSource {
        rawDataSource.open(DataSpec(RawResourceDataSource.buildRawResourceUri(id)))
        return ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(rawDataSource.uri)
    }
}