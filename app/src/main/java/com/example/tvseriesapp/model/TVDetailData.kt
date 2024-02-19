package com.example.tvseriesapp.model

data class TVDetailData(
    val adult:Boolean,
    val backdrop_path:String,
    val created_by:List<CreatedBy>,
    val episode_run_time:List<Int>,
    val first_air_date:String,
    val genres:List<Genres>,
    val homepage:String,
    val id:Int,
    val in_production:Boolean,
    val languages:List<String>,
    val last_air_date:String,
    val last_episode_to_air: LastEpisode?,
    val name:String,
    val next_episode_to_air:NextEpisodes?,
    val networks:List<Network>,
    val number_of_episodes:Int,
    val number_of_seasons:Int,
    val origin_country:List<String>,
    val original_language:String,
    val original_name:String,
    val overview:String,
    val popularity:Double,
    val poster_path:String,
    val production_companies:List<ProductionCompany>,
    val production_countries:List<ProductionCountries>,
    val seasons:List<Seasons>,
    val spoken_languages:List<Language>,
    val status:String,
    val tagline:String,
    val type:String,
    val vote_average:Double,
    val vote_count:Int
    )

data class Genres(
    val id:Int,
    val name:String
)

data class Network(
    val id:Int,
    val logo_path:String,
    val name:String,
    val origin_country:String
)

data class LastEpisode(
    val id:Int,
    val name:String,
    val overview:String,
    val vote_average:Double,
    val vote_count:Int,
    val air_date:String,
    val episode_number:Int,
    val episode_type:String,
    val production_code:String,
    val runtime:Int,
    val season_number:Int,
    val show_id:Int,
    val still_path:String
)

data class NextEpisodes(
    val id:Int,
    val name:String,
    val overview:String,
    val vote_average:Double,
    val vote_count:Int,
    val air_date:String,
    val episode_number:Int,
    val episode_type:String,
    val production_code:String,
    val runtime:Int,
    val season_number:Int,
    val show_id:Int,
    val still_path:Any
    )

data class ProductionCompany(
    val id:Int,
    val logo_path:String,
    val name:String,
    val origin_country:String
)

data class ProductionCountries(
    val iso_3166_1:String?,
    val name:String
)

data class Seasons(
    val air_date:String,
    val episode_count:Int,
    val id:Int,
    val name:String,
    val overview:String,
    val poster_path:String,
    val season_number:String,
    val vote_average:Double
)
data class Language(
    val english_name:String,
    val iso_639_1:String,
    val name:String
    )

data class CreatedBy(
    val id:Int,
    val credit_id:String,
    val name:String,
    val gender:Int,
    val profile_path:String
    )


fun TVDetailData.toSimple():TVData{
    return TVData(adult,backdrop_path,id,/*origin_country,*/original_language,original_name,overview,popularity,
        poster_path,first_air_date,name,vote_average,vote_count)
}
