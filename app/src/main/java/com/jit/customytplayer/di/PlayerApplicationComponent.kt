package com.jit.customytplayer.di

import com.jit.customytplayer.MainActivity
import com.jit.customytplayer.VideoPlayerActivity
import dagger.Component

@Component
interface PlayerApplicationComponent {

    fun getAdapterObject(activity: MainActivity)

    fun getCustomPlayerObject(playerActivity: VideoPlayerActivity)
}