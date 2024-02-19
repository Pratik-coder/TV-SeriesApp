package com.example.tvseriesapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tvseriesapp.databinding.RowPersonimagelistBinding
import com.example.tvseriesapp.model.PersonProfile
import com.example.tvseriesapp.retrofit.RetrofitClient

class PersonImageListAdapter(val context: Context, var imagesList:List<PersonProfile> = ArrayList()):RecyclerView.Adapter<PersonImageListAdapter.ViewHolder>() {


    interface OnItemClickListener{
        fun onItemClick(personData:PersonProfile)
    }

    private var onItemClickListener:OnItemClickListener?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       return ViewHolder(RowPersonimagelistBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val profile=imagesList[position]
        val imageUrl=RetrofitClient.PROFILE_PATH + profile.filePath
        Glide.with(context).load(imageUrl).into(holder.imageViewPersons)
        holder.imageViewPersons.setOnClickListener {
            onItemClickListener?.onItemClick(profile)
        }
    }

    override fun getItemCount(): Int {
       return imagesList.size
    }

    fun setOnPersonImageClickListener(listener: OnItemClickListener){
        this.onItemClickListener=listener
    }

    class ViewHolder(binding:RowPersonimagelistBinding):RecyclerView.ViewHolder(binding.root) {
        val imageViewPersons:ImageView=binding.imageViewPersons
    }
}