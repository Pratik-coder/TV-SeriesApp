package com.example.tvseriesapp.model

import com.google.gson.annotations.SerializedName

data class RemoteSeasonData(
    @SerializedName("_id") val seasonId:String?,
    @SerializedName("air_date") val airDate:String?,
    @SerializedName("episodes") val episodes:List<RemoteEpisode>?,
    @SerializedName("name") val name:String?,
    @SerializedName("overview") val overview:String?,
    @SerializedName("id") val id:Int,
    @SerializedName("poster_path") val posterPath:String?,
    @SerializedName("season_number") val seasonNumber:Int?,
    @SerializedName("vote_average") val voteAverage:Double?
)

data class RemoteEpisode(
    @SerializedName("air_date") val airDate:String?,
    @SerializedName("episode_number") val episodeNumber:Int?,
    @SerializedName("episode_type") val episodeType:String?,
    @SerializedName("id") val id:Int,
    @SerializedName("name") val name:String?,
    @SerializedName("overview") val overview:String?,
    @SerializedName("production_code") val productionCode:String?,
    @SerializedName("runtime") val runtime:Int?,
    @SerializedName("season_number") val seasonNumber:Int,
    @SerializedName("show_id") val showId:Int,
    @SerializedName("still_path") val stillPath:String?,
    @SerializedName("vote_average") val voteAverage:Double?,
    @SerializedName("vote_count") val voteCount:Int,
    @SerializedName("guest_stars") val guestStars:List<RemoteGuestStar>?
)

data class RemoteGuestStar(
    @SerializedName("character") val character:String?,
    @SerializedName("credit_id") val creditId:String?,
    @SerializedName("order") val order:Int?,
    @SerializedName("adult") val adult:Boolean?,
    @SerializedName("gender") val gender:Int?,
    @SerializedName("id") val id:Int,
    @SerializedName("known_for_department") val knownForDepartment:String?,
    @SerializedName("name") val name:String?,
    @SerializedName("original_name") val originalName:String?,
    @SerializedName("popularity") val popularity:Double?,
    @SerializedName("profile_path") val profilePath:String?
)


