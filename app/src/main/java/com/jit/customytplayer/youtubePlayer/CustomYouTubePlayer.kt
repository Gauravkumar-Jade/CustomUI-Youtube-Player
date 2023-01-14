package com.jit.customytplayer.youtubePlayer

import android.os.Handler
import android.view.View
import android.widget.SeekBar
import androidx.viewbinding.ViewBinding
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import com.jit.customytplayer.R
import com.jit.customytplayer.databinding.ActivityVideoPlayerBinding
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CustomYouTubePlayer @Inject constructor():YouTubePlayer.OnInitializedListener, YouTubePlayer.PlayerStateChangeListener,
    YouTubePlayer.PlaybackEventListener,Runnable{

    private lateinit var mYouTubePlayer:YouTubePlayer
    private var mHandler: Handler? = null
    private var mCustomPlayerListener: CustomPlayerListener? = null

    fun attachCustomPlayerListener(customPlayerListener: CustomPlayerListener){
        this.mCustomPlayerListener = customPlayerListener
    }

    fun detachCustomPlayerListener(){
        this.mCustomPlayerListener = null
    }

    fun setInitializePlayer(youTubePlayerView: YouTubePlayerView?){
        youTubePlayerView?.initialize("AIzaSyBx7v0YOb140fDO7EbfMx4l87raxezDWFw", this)
        mHandler = Handler()
    }

    fun addVideo(videoID:String){
        mYouTubePlayer?.cueVideo(videoID)
    }

    override fun onInitializationSuccess(provider: YouTubePlayer.Provider?, player: YouTubePlayer?,
        wasRestored: Boolean) {

        if(player == null) return
        mYouTubePlayer = player


        displayCurrentTime()

        player.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS)
        player.fullscreenControlFlags = 2
        player.setPlayerStateChangeListener(this)
        player.setPlaybackEventListener(this)
        player.setFullscreen(false)
        mCustomPlayerListener?.onInitialization()
        mCustomPlayerListener?.onGetYouTubePlayer(mYouTubePlayer)
    }

    override fun onInitializationFailure(provider: YouTubePlayer.Provider?, result: YouTubeInitializationResult?) {
    }

    override fun onLoading() {
    }

    override fun onLoaded(p0: String?) {
        displayCurrentTime()
        mCustomPlayerListener?.onVideoLoaded()
    }

    override fun onAdStarted() {
    }

    override fun onVideoStarted() {
        displayCurrentTime()
    }

    override fun onVideoEnded() {
        mCustomPlayerListener?.onVideoEnded()
    }

    override fun onError(p0: YouTubePlayer.ErrorReason?) {
    }

    override fun onPlaying() {
        mHandler?.postDelayed(this, 100)
        mCustomPlayerListener?.onVideoPlaying()
    }

    override fun onPaused() {
        mHandler?.removeCallbacks(this)
        mCustomPlayerListener?.onVideoPaused()
    }

    override fun onStopped() {
        mHandler?.removeCallbacks(this)
        mCustomPlayerListener?.onVideoStopped()
    }

    override fun onBuffering(p0: Boolean) {
    }

    override fun onSeekTo(p0: Int) {
        mHandler?.postDelayed(this, 100)
    }

    private fun displayCurrentTime(){
        if(mYouTubePlayer == null) return
        val totalTime = formatTime(mYouTubePlayer.durationMillis.toLong())
        val playTime = formatTime(mYouTubePlayer.currentTimeMillis.toLong())
        val currentTime = mYouTubePlayer.currentTimeMillis

        mCustomPlayerListener?.onDisplayTimer(playTime, totalTime, currentTime )

    }

    private fun formatTime(millisecond:Long):String{

        val hour = TimeUnit.MILLISECONDS.toHours(millisecond)
        val minute = TimeUnit.MILLISECONDS.toMinutes(millisecond)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(millisecond)

        return (if (hour == 0L){
            String.format("%02d:%02d", minute, seconds % 60)
        } else {
            String.format("%02d:%02d%:%02d",hour, minute, seconds % 60)
        }).toString()
    }

    override fun run() {
        displayCurrentTime()
        mHandler?.postDelayed(this,100)
    }
}