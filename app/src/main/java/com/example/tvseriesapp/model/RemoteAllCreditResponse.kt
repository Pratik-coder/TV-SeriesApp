package com.example.tvseriesapp.model

import com.google.gson.annotations.SerializedName

data class RemoteAllCreditResponse(
    @SerializedName("cast") val cast:List<PersonCast>,
    @SerializedName("id") val id:Int
)

data class PersonCast(
    @SerializedName("adult") val adult:Boolean,
    @SerializedName("backdrop_path") val backdropPath:Boolean,
    @SerializedName("genre_ids") val genreIds:List<Int>,
    @SerializedName("id") val id:Int,
    @SerializedName("origin_country") val originCountry:List<String>,
    @SerializedName("original_language") val originalLanguage:String,
    @SerializedName("original_name") val originalName:String,
    @SerializedName("original_title") val originalTitle:String,
    @SerializedName("title") val title:String,
    @SerializedName("overview") val overview:String,
    @SerializedName("popularity") val popularity:Double,
    @SerializedName("poster_path") val posterPath:String,
    @SerializedName("first_air_date") val firstAirDate:String,
    @SerializedName("name") val name:String,
    @SerializedName("vote_average") val voteAverage:Double,
    @SerializedName("vote_count") val voteCount:Int,
    @SerializedName("character") val character:String,
    @SerializedName("credit_id") val creditId:String,
    @SerializedName("episode_count") val episodeCount:Int,
    @SerializedName("media_type") val mediaType:String,
)
