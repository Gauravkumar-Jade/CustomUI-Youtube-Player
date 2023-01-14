package com.jit.customytplayer.youtubePlayer

import com.google.android.youtube.player.YouTubePlayer

interface CustomPlayerListener {

    fun onInitialization()
    fun onGetYouTubePlayer(youTubePlayer: YouTubePlayer)
    fun onVideoLoaded()
    fun onVideoEnded()
    fun onVideoPlaying()
    fun onVideoPaused()
    fun onVideoStopped()
    fun onDisplayTimer(playTime: String, totalTime: String, currentTimeMillis: Int)
}