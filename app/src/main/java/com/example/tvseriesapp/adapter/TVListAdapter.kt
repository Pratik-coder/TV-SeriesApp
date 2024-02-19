package com.example.tvseriesapp.adapter

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.tvseriesapp.R
import com.example.tvseriesapp.databinding.ActivityHomeBinding
import com.example.tvseriesapp.databinding.RowTvListBinding
import com.example.tvseriesapp.extensions.dp
import com.example.tvseriesapp.extensions.load
import com.example.tvseriesapp.model.TVData
import com.example.tvseriesapp.retrofit.RetrofitClient
import org.w3c.dom.Text
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class TVListAdapter(val context: Context,var tvList:List<TVData> = ArrayList()):RecyclerView.Adapter<TVListAdapter.ViewHolder>()
{
       interface OnItemClickListener
       {
           fun onItemClick(tvData: TVData,container:View)
       }

  private var onItemTVImageClickListener:OnItemClickListener?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      return ViewHolder(RowTvListBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val airingTvList=tvList[position]
       holder.textViewSeriesTitle.text=airingTvList.name
       holder.textViewSeriesDescription.text=context.getString(R.string.tv_row_desc_pattern,
       if (airingTvList.first_air_date.isNotEmpty())
        LocalDate.parse(airingTvList.first_air_date).format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))
       else
           context.getString(R.string.str_noreleasedate),getRating(airingTvList))
        holder.imageViewPoster.setImageResource(R.drawable.poster_placeholder)
        holder.imageViewPoster.transitionName=airingTvList.id.toString()
        holder.linearLayout.setBackgroundColor(Color.DKGRAY)

       holder.imageViewPoster.load(url=RetrofitClient.POSTER_BASE_URL + airingTvList.poster_path,
       crossFade = true, width = 160.dp, height = 160.dp){
           color ->
           holder.linearLayout.setBackgroundColor(color)
       }
       holder.cardView.setOnClickListener {
           onItemTVImageClickListener?.onItemClick(airingTvList,holder.imageViewPoster)
       }

    }

    override fun getItemCount(): Int {
    return tvList.size
    }


    fun fillList(items:List<TVData>){
        this.tvList+=items
        notifyDataSetChanged()
    }

    fun clear(){
        this.tvList= emptyList()
    }

    fun getRating(tvData: TVData):String{
        return if (tvData.vote_count==0 && context!=null){
            context.getString(R.string.str_norating)
        }
        else{
            val starIcon=9733.toChar()
             "${tvData.vote_average} $starIcon"
        }
    }

    fun setOnTVClickListener(listener: OnItemClickListener){
        this.onItemTVImageClickListener=listener
    }


    class ViewHolder(binding: RowTvListBinding):RecyclerView.ViewHolder(binding.root) {
        val cardView:CardView=binding.cvTvContainer
        val linearLayout:LinearLayout=binding.llTextContainer
        val textViewSeriesTitle:TextView=binding.tvTvseriesTitle
        val textViewSeriesDescription:TextView=binding.tvTvseriesDescription
        val imageViewPoster:ImageView=binding.ivTvPoster
    }
}