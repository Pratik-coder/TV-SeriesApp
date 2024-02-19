package com.example.tvseriesapp.model

import com.google.gson.annotations.SerializedName

data class RemotePersonImages(
    @SerializedName("id") val id:Int,
    @SerializedName("profiles") val profiles:List<PersonProfile>,
)

data class PersonProfile(
    @SerializedName("aspect_ratio") val aspectRatio:Double,
    @SerializedName("height") val height:Int,
    @SerializedName("iso_639_1") val strIso:String?=null,
    @SerializedName("file_path") val filePath:String,
    @SerializedName("vote_average") val voteAverage:Double,
    @SerializedName("vote_count") val voteCount:Int,
    @SerializedName("width") val width:Int,
)
