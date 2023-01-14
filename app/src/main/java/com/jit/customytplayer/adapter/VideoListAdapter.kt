package com.jit.customytplayer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jit.customytplayer.data.VideosData
import com.jit.customytplayer.databinding.CustomListBinding
import javax.inject.Inject

class VideoListAdapter @Inject constructor():RecyclerView.Adapter<VideoListAdapter.VideoListHolder>() {

    val videoList = VideosData.getVideos()

    private var mAdapterListener: AdapterListener? = null

    fun attachAdapterListener(adapterListener: AdapterListener){
        this.mAdapterListener = adapterListener
    }

    fun detachAdapterListener(){
        this.mAdapterListener = null
    }

    inner class VideoListHolder(val binding: CustomListBinding):RecyclerView.ViewHolder(binding.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoListHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = CustomListBinding.inflate(inflater,parent,false)
        return VideoListHolder(view)
    }

    override fun onBindViewHolder(holder: VideoListHolder, position: Int) {
        val index = holder.adapterPosition

        val videoId = videoList[index].video_id
        val videoName = videoList[index].video_name

        val url = "https://img.youtube.com/vi/${videoId}/maxresdefault.jpg"

        holder.binding.apply {
            Glide.with(holder.itemView.context).load(url).into(ivVideo)
            tvMovieTitle.text = videoName

            ivPlayBt.setOnClickListener { mAdapterListener?.onPlayVideo(videoId) }
        }
    }

    override fun getItemCount(): Int {
        return videoList.size
    }
}