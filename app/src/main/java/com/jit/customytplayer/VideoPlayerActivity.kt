package com.jit.customytplayer

import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubePlayer
import com.jit.customytplayer.databinding.ActivityVideoPlayerBinding
import com.jit.customytplayer.youtubePlayer.CustomPlayerListener
import com.jit.customytplayer.youtubePlayer.CustomYouTubePlayer
import javax.inject.Inject

class VideoPlayerActivity : YouTubeBaseActivity(),CustomPlayerListener, SeekBar.OnSeekBarChangeListener,View.OnClickListener {
    companion object{
        const val VIDEO_ID = "video_id"
    }

    private lateinit var binding: ActivityVideoPlayerBinding
    private lateinit var mYouTubePlayer: YouTubePlayer

    @Inject lateinit var customYouTubePlayer: CustomYouTubePlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        (applicationContext as MyApplication).applicationComponent.getCustomPlayerObject(this)

        onInitializePlayer()
    }

    private fun onInitializePlayer() {

        customYouTubePlayer.apply {
            setInitializePlayer(binding.youtubePlayerView)
            attachCustomPlayerListener(this@VideoPlayerActivity)
        }
    }

    override fun onInitialization() {
        val videoId = intent.extras?.get(VIDEO_ID)?.toString()
        customYouTubePlayer.addVideo(videoId!!)
        binding.videoSeekbar.setOnSeekBarChangeListener(this)
        binding.ivPlay.setOnClickListener(this)
    }

    override fun onGetYouTubePlayer(youTubePlayer: YouTubePlayer) {
        this.mYouTubePlayer = youTubePlayer
    }

    override fun onVideoLoaded() {
        binding.videoSeekbar.max = mYouTubePlayer.durationMillis
    }

    override fun onVideoEnded() {
        mYouTubePlayer.seekToMillis(0)
        mYouTubePlayer.pause()
    }

    override fun onVideoPlaying() {
        binding.ivPlay.setImageResource(R.drawable.ic_baseline_pause)
    }

    override fun onVideoPaused() {
        binding.ivPlay.setImageResource(R.drawable.ic_baseline_play)
    }

    override fun onVideoStopped() {
        binding.ivPlay.setImageResource(R.drawable.ic_baseline_play)
    }

    override fun onDisplayTimer(playTime: String, totalTime: String, currentTimeMillis: Int) {
        binding.apply {
            tvPlayTime.text = playTime
            tvTotalTime.text = totalTime
            videoSeekbar.progress = currentTimeMillis
        }
    }


    override fun onStop() {
        super.onStop()
        customYouTubePlayer.detachCustomPlayerListener()
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        if(fromUser){
            mYouTubePlayer.seekToMillis(progress)
        }
    }

    override fun onStartTrackingTouch(p0: SeekBar?) {
    }

    override fun onStopTrackingTouch(p0: SeekBar?) {
    }

    override fun onClick(p0: View?) {
        if( mYouTubePlayer != null &&  mYouTubePlayer.isPlaying){
            mYouTubePlayer.pause()
        }
        if ( mYouTubePlayer != null && ! mYouTubePlayer.isPlaying){
            mYouTubePlayer.play()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mYouTubePlayer.release()
    }
}