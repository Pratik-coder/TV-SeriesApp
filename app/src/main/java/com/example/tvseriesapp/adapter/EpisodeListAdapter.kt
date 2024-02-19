package com.example.tvseriesapp.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tvseriesapp.R
import com.example.tvseriesapp.databinding.RowEpisodelistBinding
import com.example.tvseriesapp.extensions.dp
import com.example.tvseriesapp.extensions.load
import com.example.tvseriesapp.model.Episode
import com.example.tvseriesapp.retrofit.RetrofitClient
import org.w3c.dom.Text

class EpisodeListAdapter(val context:Context,var episodeList:List<Episode> = ArrayList()):RecyclerView.Adapter<EpisodeListAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RowEpisodelistBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val episodeData=episodeList[position]
        holder.textViewEpisodeName.text=episodeData.name
        holder.imageViewEpisode.setImageResource(R.drawable.poster_placeholder)
        holder.imageViewEpisode.transitionName=episodeData.id.toString()
        holder.linearLayoutEpisode.setBackgroundColor(Color.DKGRAY)
        holder.imageViewEpisode.load(url=RetrofitClient.POSTER_BASE_URL + episodeData.still_path,
        crossFade = true, width = 160.dp, height = 160.dp){
            color ->
            holder.linearLayoutEpisode.setBackgroundColor(color)
        }
    }

    override fun getItemCount(): Int {
        return episodeList.size
    }

    fun clear(){
        this.episodeList= emptyList()
    }

    fun fillList(episodes:List<Episode>){
        this.episodeList+=episodes
        notifyDataSetChanged()
    }

    class ViewHolder(binding: RowEpisodelistBinding):RecyclerView.ViewHolder(binding.root) {
        val textViewEpisodeName:TextView=binding.tvTvepisodeName
        val imageViewEpisode:ImageView=binding.ivTvepisodePoster
        val linearLayoutEpisode:LinearLayout=binding.llTextContainer
    }
}