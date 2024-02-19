package com.example.tvseriesapp.model

import com.google.gson.annotations.SerializedName

data class RemoteVideoResponse(
    @SerializedName("id") val id:Int,
    @SerializedName("results") val results:List<Videos>
)

data class Videos(
    @SerializedName("iso_639_1") val strIso:String,
    @SerializedName("iso_3166_1") val strIso2:String,
    @SerializedName("name") val name:String,
    @SerializedName("key") val key:String,
    @SerializedName("published_at") val publishedAt:String,
    @SerializedName("site") val site:String,
    @SerializedName("size") val size:Int,
    @SerializedName("type") val type:String,
    @SerializedName("official") val official:Boolean,
    @SerializedName("id") val id:String,
)
