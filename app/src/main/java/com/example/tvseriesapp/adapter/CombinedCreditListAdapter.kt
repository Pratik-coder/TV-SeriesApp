package com.example.tvseriesapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tvseriesapp.R
import com.example.tvseriesapp.databinding.RowAllcreditsBinding
import com.example.tvseriesapp.model.PersonCast
import com.example.tvseriesapp.model.TVData
import com.example.tvseriesapp.retrofit.RetrofitClient

class CombinedCreditListAdapter(val context:Context,var combinedCreditList:List<PersonCast> = ArrayList()):RecyclerView.Adapter<CombinedCreditListAdapter.ViewHolder>() {

    interface OnItemClickListener
    {
        fun onItemClick(creditData:PersonCast)
    }

    private var onItemCreditClickListener:OnItemClickListener?=null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       return ViewHolder(RowAllcreditsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val combinedData=combinedCreditList[position]
       val castUrl=RetrofitClient.POSTER_BASE_URL + combinedData.posterPath
        Glide.with(context).load(castUrl).error(R.drawable.poster_placeholder).into(holder.imageViewPersonCredit)
       if (combinedData.mediaType=="tv"){
           holder.textViewCreditName.text=combinedData.originalName
       }
        else{
           holder.textViewCreditName.text=combinedData.originalTitle
       }

        holder.linearLayoutCredits.setOnClickListener {
            onItemCreditClickListener?.onItemClick(combinedData)
        }
    }

    override fun getItemCount(): Int {
       return combinedCreditList.size
    }

    fun setOnCreditClickListener(listener: OnItemClickListener){
        this.onItemCreditClickListener=listener
    }

    class ViewHolder(binding:RowAllcreditsBinding):RecyclerView.ViewHolder(binding.root) {
        val imageViewPersonCredit:ImageView=binding.imageViewPersonCredit
        val textViewCreditName:TextView=binding.textViewCreditName
        val linearLayoutCredits:LinearLayout=binding.linearLayoutCredits
    }
}