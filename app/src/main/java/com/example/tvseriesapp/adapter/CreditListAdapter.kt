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
import com.bumptech.glide.Glide
import com.example.tvseriesapp.R
import com.example.tvseriesapp.databinding.RowCreditBinding
import com.example.tvseriesapp.extensions.dp
import com.example.tvseriesapp.extensions.dpToPx
import com.example.tvseriesapp.extensions.load
import com.example.tvseriesapp.model.Cast
import com.example.tvseriesapp.retrofit.RetrofitClient

class CreditListAdapter(val context:Context,val castList:List<Cast> = ArrayList()):RecyclerView.Adapter<CreditListAdapter.CreditViewHolder>() {

    interface OnItemClickListener{
        fun onItemClick(cast: Cast)
    }
    private  var onItemCastClickListener:OnItemClickListener?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreditViewHolder {
        return CreditViewHolder(RowCreditBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: CreditViewHolder, position: Int) {
         val castData= castList[position]
         holder.tvCreditName.text=castData.originalName
         val creditImageUrl=RetrofitClient.PROFILE_PATH + castData.profilePath
         Glide.with(context).load(creditImageUrl).error(R.drawable.poster_placeholder).into(holder.ivTvSeasonCredit)
         holder.linearLayoutCredits.setOnClickListener {
            onItemCastClickListener?.onItemClick(castData)
        }
    }

    override fun getItemCount(): Int {
       return castList.size
    }

    fun setOnCastClickListener(listener:OnItemClickListener){
       this.onItemCastClickListener=listener
    }

    class CreditViewHolder(binding:RowCreditBinding):RecyclerView.ViewHolder(binding.root){
       val ivTvSeasonCredit:ImageView=binding.ivTvSeasonCredit
       val tvCreditName:TextView=binding.textViewCastName
       val linearLayoutCredits:LinearLayout=binding.linearLayoutCredits
    }
}