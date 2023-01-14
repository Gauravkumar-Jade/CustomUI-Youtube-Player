package com.jit.customytplayer.di

import com.jit.customytplayer.MainActivity
import dagger.Component

@Component
interface AdapterComponent {

    fun inject(activity: MainActivity)
}