package com.jit.customytplayer

import android.app.Application
import com.jit.customytplayer.di.DaggerPlayerApplicationComponent

class MyApplication:Application() {

    val applicationComponent = DaggerPlayerApplicationComponent.create()
}