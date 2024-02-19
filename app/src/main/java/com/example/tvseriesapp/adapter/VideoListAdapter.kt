package com.example.tvseriesapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tvseriesapp.R
import com.example.tvseriesapp.databinding.RowVideolistBinding
import com.example.tvseriesapp.model.Videos
import com.example.tvseriesapp.retrofit.RetrofitClient

class VideoListAdapter(val context: Context,val videoList:List<Videos> = ArrayList()):RecyclerView.Adapter<VideoListAdapter.ViewHolder>() {

    interface OnItemClickListener{
        fun onItemClick(videos: Videos)
    }

    private  var onItemClickListener: OnItemClickListener?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
          return ViewHolder(RowVideolistBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val videoData=videoList[position]
       val imageUrl=RetrofitClient.YOUTUBE_IMAGE_URL + videoData.key+"/hqdefault.jpg"
       Glide.with(context).load(imageUrl).error(R.drawable.youtubenothumbnail).into(holder.imageViewVideo)
        holder.imageViewVideo.setOnClickListener {
            onItemClickListener?.onItemClick(videoData)
        }
    }

    override fun getItemCount(): Int {
       return videoList.size
    }

    fun setOnVideoClickListener(listener:OnItemClickListener){
        this.onItemClickListener=listener
    }

    class ViewHolder(binding:RowVideolistBinding):RecyclerView.ViewHolder(binding.root) {
        val imageViewVideo:ImageView=binding.imageViewVideo
    }
}