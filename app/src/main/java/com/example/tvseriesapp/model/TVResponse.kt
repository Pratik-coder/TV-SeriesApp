package com.example.tvseriesapp.model

import com.google.gson.annotations.SerializedName

data class TVResponse(
    @SerializedName("adult")
    var adult:Boolean?,

    @SerializedName("backdrop_path")
    var backdrop_path:String?,

    @SerializedName("id")
    var id:Int,

    /*@SerializedName("origin_country")
    var origin_country:List<String>,
*/
    @SerializedName("original_language")
    var original_language:String?,

    @SerializedName("original_name")
    var original_name:String?,

    @SerializedName("overview")
    var overview:String?,

    @SerializedName("popularity")
    var popularity:Double?,

    @SerializedName("poster_path")
    var poster_path:String?,

    @SerializedName("first_air_date")
    var first_air_date:String?,

    @SerializedName("name")
    var name:String?,

    @SerializedName("vote_average")
    var vote_average:Double?,

    @SerializedName("vote_count")
    var vote_count:Int?,
    )
