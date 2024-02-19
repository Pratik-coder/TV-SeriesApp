package com.example.tvseriesapp.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.tvseriesapp.R
import com.example.tvseriesapp.databinding.RowTvseasonlistBinding
import com.example.tvseriesapp.extensions.dp
import com.example.tvseriesapp.extensions.load
import com.example.tvseriesapp.model.SeasonDeatilData
import com.example.tvseriesapp.model.Seasons
import com.example.tvseriesapp.retrofit.RetrofitClient
import com.example.tvseriesapp.ui.SeasonDetails

class SeasonListAdapter(val context: Context,var seasonList:List<Seasons> = ArrayList()):RecyclerView.Adapter<SeasonListAdapter.SeasonViewHolder>() {

   interface OnItemClickListener{
       fun onItemClick(seasons: Seasons)
   }
   private var onItemClickListener:OnItemClickListener?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeasonViewHolder {
        return SeasonViewHolder(RowTvseasonlistBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: SeasonViewHolder, position: Int) {
        val seasonData=seasonList[position]
        holder.tvSeasonName.text=seasonData.name
        holder.tvSeasonNmuber.text=seasonData.season_number
        holder.ivSeasonPoster.setImageResource(R.drawable.poster_placeholder)
        holder.ivSeasonPoster.transitionName=seasonData.id.toString()
        holder.linearLayout.setBackgroundColor(Color.DKGRAY)
        holder.ivSeasonPoster.load(url = RetrofitClient.POSTER_BASE_URL + seasonData.poster_path,
        crossFade = true, width = 160.dp, height = 160.dp){
            color->
            holder.linearLayout.setBackgroundColor(color)
        }
        holder.cardViewSaeson.setOnClickListener {
            onItemClickListener?.onItemClick(seasonData)
        }
    }

    override fun getItemCount(): Int {
        return seasonList.size
    }

    fun clear(){
        this.seasonList= emptyList()
    }

    fun fillList(list:List<Seasons>){
        this.seasonList+=list
        notifyDataSetChanged()
    }

    fun setOnSeasonClickListener(listener:OnItemClickListener){
        this.onItemClickListener=listener
    }
    class SeasonViewHolder(binding: RowTvseasonlistBinding):RecyclerView.ViewHolder(binding.root) {
         val tvSeasonName:TextView=binding.tvTvseasonName
         val tvSeasonNmuber:TextView=binding.tvTvseasonNumber
         val ivSeasonPoster:ImageView=binding.ivTvseasonPoster
         val linearLayout:LinearLayout=binding.llTextContainer
         val cardViewSaeson:CardView=binding.cvTvseasonContainer

    }
}