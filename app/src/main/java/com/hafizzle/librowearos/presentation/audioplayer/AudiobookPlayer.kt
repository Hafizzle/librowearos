package com.hafizzle.librowearos.presentation.audioplayer

import android.content.Context
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util


class AudiobookPlayer(private val context: Context) {

    private val exoPlayer: SimpleExoPlayer by lazy {
        SimpleExoPlayer.Builder(context).build()
    }

    fun play(url: String) {
        val dataSourceFactory = DefaultDataSourceFactory(context, Util.getUserAgent(context, "librowearos"))
        val mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(
            MediaItem.fromUri(url))
        exoPlayer.prepare(mediaSource)
        exoPlayer.playWhenReady = true
    }

    fun pause() {
        exoPlayer.playWhenReady = false
    }

    fun stop() {
        exoPlayer.stop()
    }

    fun seekTo(position: Long) {
        exoPlayer.seekTo(position)
    }

    fun getCurrentPosition(): Long {
        return exoPlayer.currentPosition
    }

    fun getDuration(): Long {
        return exoPlayer.duration
    }

    fun release() {
        exoPlayer.release()
    }
}
