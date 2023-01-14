package com.jit.customytplayer

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jit.customytplayer.adapter.AdapterListener
import com.jit.customytplayer.adapter.VideoListAdapter
import com.jit.customytplayer.databinding.ActivityMainBinding
import javax.inject.Inject


class MainActivity: AppCompatActivity(), AdapterListener{

    private lateinit var binding: ActivityMainBinding
    @Inject lateinit var videoListAdapter: VideoListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        (applicationContext as MyApplication).applicationComponent.getAdapterObject(this)

        videoListAdapter.attachAdapterListener(this)
        binding.rvVideolist.adapter = videoListAdapter
        supportActionBar?.title = "Custom Youtube Player"
    }

    override fun onPlayVideo(videoId: String) {

        startActivity(Intent(this@MainActivity,VideoPlayerActivity::class.java)
            .putExtra(VideoPlayerActivity.VIDEO_ID,videoId))
    }

    override fun onDestroy() {
        videoListAdapter.detachAdapterListener()
        super.onDestroy()
    }
}