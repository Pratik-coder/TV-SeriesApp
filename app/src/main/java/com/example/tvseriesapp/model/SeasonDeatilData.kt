package com.example.tvseriesapp.model

data class SeasonDeatilData (
    val _id:String,
    val air_date:String,
    val episodes:List<Episode>,
    val name:String,
    val overview:String,
    val id:Int,
    val poster_path:String,
    val season_number:Int,
    val vote_average:Double
        )


data class Episode(
        val air_date:String,
        val episode_number:Int,
        val episode_type:String,
        val id:Int,
        val name:String,
        val overview:String,
        val production_code:String,
        val runtime:Int,
        val season_number:Int,
        val show_id:Int,
        val still_path:String,
        val vote_average:Double,
        val vote_count:Int,
        val guest_stars:List<GuestStar>
        )


data class GuestStar(
        val character:String,
        val credit_id:String,
        val order:Int,
        val adult:Boolean,
        val gender:Int,
        val id:Int,
        val known_for_department:String,
        val name:String,
        val original_name:String,
        val popularity:Double,
        val profile_path:String
        )
