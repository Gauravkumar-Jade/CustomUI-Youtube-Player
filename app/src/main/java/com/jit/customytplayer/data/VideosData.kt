package com.jit.customytplayer.data

import com.jit.customytplayer.model.Videos

object VideosData {

    fun getVideos():ArrayList<Videos>{
        return arrayListOf(
            Videos("Avatar","s_ZzHEOqxQI"),
            Videos("John Wick", "xVMkyDSqlH4"),
            Videos("Quantumania","9kyPX1qZbfY"),
            Videos("Big Bunny","-m3V8w_7vhk")
        )
    }
}