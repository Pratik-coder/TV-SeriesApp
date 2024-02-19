package com.example.tvseriesapp.model

import com.google.gson.annotations.SerializedName

data class RemoteTVDetailResponse(
    @SerializedName("adult")
    val adult:Boolean?,

    @SerializedName("backdrop_path")
    val backdrop_path:String?,

    @SerializedName("created_by")
    val created_by:List<RemoteCreatedBy>?,

    @SerializedName("episode_run_time")
    val episode_run_time:List<Int>?,

    @SerializedName("first_air_date")
    val first_air_date:String?,

    @SerializedName("genres")
    val genres:List<RemoteGenres>?,

    @SerializedName("homepage")
    val homepage:String?,

    @SerializedName("id")
    val id:Int,

    @SerializedName("in_production")
    val in_production:Boolean?,

    @SerializedName("languages")
    val languages:List<String>?,

    @SerializedName("last_air_date")
    val last_air_date:String?,

    @SerializedName("last_episode_to_air")
    val last_episode_to_air:RemoteLastEpisodeToAir?,

    @SerializedName("name")
    val name:String?,

    @SerializedName("next_episode_to_air")
    val next_episode_to_air:RemoteNextEpisodeToAir?,

    @SerializedName("networks")
    val networks:List<RemoteNetworks>?,

    @SerializedName("number_of_episodes")
    val number_of_episodes:Int?,

    @SerializedName("number_of_seasons")
    val number_of_seasons:Int?,

    @SerializedName("origin_country")
    val origin_country:List<String>?,

    @SerializedName("original_language")
    val original_language:String?,

    @SerializedName("original_name")
    val original_name:String?,

    @SerializedName("overview")
    val overview:String?,

    @SerializedName("popularity")
    val popularity:Double?,

    @SerializedName("poster_path")
    val poster_path:String?,

    @SerializedName("production_companies")
    val production_companies:List<RemoteProductionCompanies>?,

    @SerializedName("production_countries")
    val production_countries:List<RemoteProductionCountry>?,

    @SerializedName("seasons")
    val seasons:List<RemoteSeason>?,

    @SerializedName("spoken_languages")
    val spoken_languages:List<RemoteSpokenLanguage>?,

    @SerializedName("status")
    val status:String?,

    @SerializedName("tagline")
    val tagline:String?,

    @SerializedName("type")
    val type:String?,

    @SerializedName("vote_average")
    val vote_average:Double?,

    @SerializedName("vote_count")
    val vote_count:Int?
    )

data class RemoteGenres(
    @SerializedName("id") val id:Int,
    @SerializedName("name") val name:String?
)

data class RemoteNetworks(
    @SerializedName("id") val id:Int,
    @SerializedName("logo_path") val logo_path:String?,
    @SerializedName("name") val name:String,
    @SerializedName("origin_country") val origin_country:String?
)

data class RemoteLastEpisodeToAir (
    @SerializedName("id") val id:Int,
    @SerializedName("name") val name:String?,
    @SerializedName("overview") val overview:String?,
    @SerializedName("vote_average")val vote_average:Double?,
    @SerializedName("vote_count") val vote_count:Int?,
    @SerializedName("air_date") val air_date:String?,
    @SerializedName("episode_number") val episode_number:Int?,
    @SerializedName("episode_type") val episode_type:String?,
    @SerializedName("production_code") val production_code:String,
    @SerializedName("runtime") val runtime:Int?,
    @SerializedName("season_number") val season_number:Int?,
    @SerializedName("show_id") val show_id:Int?,
    @SerializedName("still_path") val still_path:String?
    )

data class RemoteNextEpisodeToAir(
    @SerializedName("id") val id:Int,
    @SerializedName("name") val name:String?,
    @SerializedName("overview") val overview:String?,
    @SerializedName("vote_average")val vote_average:Double?,
    @SerializedName("vote_count") val vote_count:Int?,
    @SerializedName("air_date") val air_date:String?,
    @SerializedName("episode_number") val episode_number:Int?,
    @SerializedName("episode_type") val episode_type:String?,
    @SerializedName("production_code") val production_code:String,
    @SerializedName("runtime") val runtime:Int?,
    @SerializedName("season_number") val season_number:Int?,
    @SerializedName("show_id") val show_id:Int?,
    @SerializedName("still_path") val still_path:String?
)

data class RemoteProductionCompanies(
    @SerializedName("id")val id:Int,
    @SerializedName("logo_path") val logo_path:String?,
    @SerializedName("name") val name:String,
    @SerializedName("origin_country") val origin_country:String?
)

data class RemoteProductionCountry(
    @SerializedName("iso_3166_1") val iso_3166_1:String?,
    @SerializedName("name") val name:String,
)

data class RemoteSeason(
    @SerializedName("air_date") val air_date:String?,
    @SerializedName("episode_count") val episode_count:Int?,
    @SerializedName("id") val id:Int,
    @SerializedName("name") val name:String?,
    @SerializedName("overview") val overview:String?,
    @SerializedName("poster_path") val poster_path:String?,
    @SerializedName("season_number") val season_number:String?,
    @SerializedName("vote_average") val vote_average:Double?
    )


data class RemoteSpokenLanguage(
    @SerializedName("english_name") val english_name:String?,
    @SerializedName("iso_639_1") val iso_639_1:String?,
    @SerializedName("name") val name:String?
    )

data class RemoteCreatedBy(
    @SerializedName("id") val id:Int,
    @SerializedName("credit_id") val credit_id:String,
    @SerializedName("name") val name:String,
    @SerializedName("gender") val gender:Int,
    @SerializedName("profile_path")  val profile_path:String
)


