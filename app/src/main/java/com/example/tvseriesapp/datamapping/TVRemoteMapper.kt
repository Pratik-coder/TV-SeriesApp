package com.example.tvseriesapp.datamapping

import com.example.tvseriesapp.extensions.orDefault
import com.example.tvseriesapp.extensions.orFalse
import com.example.tvseriesapp.extensions.orNa
import com.example.tvseriesapp.model.*

class TVRemoteMapper {

    fun mapFromRemote(remoteTVResponse: RemoteTVResponse):MainTVData{
        return MainTVData(remoteTVResponse.page,remoteTVResponse.total_results,
            remoteTVResponse.total_pages,remoteTVResponse.results.map {remoteTv->
                TVData(remoteTv.adult.orFalse(),remoteTv.backdrop_path.orEmpty(),remoteTv.id,/*remoteTv.origin_country,*/
                remoteTv.original_language.orEmpty(),remoteTv.original_name.orEmpty(),remoteTv.overview.orEmpty(),
                remoteTv.popularity.orDefault(),remoteTv.poster_path.orEmpty(),remoteTv.first_air_date.orEmpty(),
                remoteTv.name.orEmpty(),remoteTv.vote_average.orDefault(),remoteTv.vote_count.orDefault())
            }
        )
    }

  fun mapLastEpisodeFromRemote(remoteLastEpisodeToAir: RemoteLastEpisodeToAir?):LastEpisode?{
    return remoteLastEpisodeToAir?.let {
        LastEpisode(it.id,it.name.orEmpty(),it.overview.orEmpty(),it.vote_average.orDefault(),it.vote_count.orDefault(),it.air_date.orEmpty(),
        it.episode_number.orDefault(),it.episode_type.orEmpty(),it.production_code.orEmpty(),it.runtime.orDefault(),it.season_number.orDefault(),
        it.show_id.orDefault(),it.still_path.orNa())
    }
  }

    fun mapNextEpisodeFromRemote(remoteNextEpisodeToAir: RemoteNextEpisodeToAir?):NextEpisodes?{
        return remoteNextEpisodeToAir?.let {
            NextEpisodes(it.id,it.name.orEmpty(),it.overview.orEmpty(),it.vote_average.orDefault(),it.vote_count.orDefault(),it.air_date.orEmpty(),
                it.episode_number.orDefault(),it.episode_type.orEmpty(),it.production_code.orEmpty(),it.runtime.orDefault(),it.season_number.orDefault(),
                it.show_id.orDefault(),it.still_path.orNa())
        }
    }

 fun mapDetailFromRemote(remoteTVDetailResponse: RemoteTVDetailResponse):TVDetailData{
     return TVDetailData(remoteTVDetailResponse.adult.orFalse(),remoteTVDetailResponse.backdrop_path.orEmpty(),
               remoteTVDetailResponse.created_by.orEmpty().map {
               CreatedBy(it.id, it.credit_id,it.name.orEmpty(),it.gender,it.profile_path.orNa())
               },
         remoteTVDetailResponse.episode_run_time.orEmpty(),remoteTVDetailResponse.first_air_date.orEmpty(),
         remoteTVDetailResponse.genres.orEmpty().map {
             Genres(it.id,it.name.orEmpty())},
         remoteTVDetailResponse.homepage.orEmpty(),remoteTVDetailResponse.id,remoteTVDetailResponse.in_production.orFalse(),
         remoteTVDetailResponse.languages.orEmpty(),remoteTVDetailResponse.last_air_date.orEmpty(),
         last_episode_to_air = mapLastEpisodeFromRemote(remoteTVDetailResponse.last_episode_to_air),remoteTVDetailResponse.name.orEmpty(),
         next_episode_to_air = mapNextEpisodeFromRemote(remoteTVDetailResponse.next_episode_to_air),
         remoteTVDetailResponse.networks.orEmpty().map {
             Network(it.id,it.logo_path.orEmpty(),it.name.orEmpty(),it.origin_country.orEmpty())
         },remoteTVDetailResponse.number_of_episodes.orDefault(),remoteTVDetailResponse.number_of_seasons.orDefault(),
         remoteTVDetailResponse.origin_country.orEmpty(),remoteTVDetailResponse.original_language.orEmpty(),remoteTVDetailResponse.original_name.orEmpty(),
         remoteTVDetailResponse.overview.orEmpty(),remoteTVDetailResponse.popularity.orDefault(),remoteTVDetailResponse.poster_path.orNa(),
         remoteTVDetailResponse.production_companies.orEmpty().map {
             ProductionCompany(it.id,it.logo_path.orEmpty(),it.name.orEmpty(),it.origin_country.orEmpty())
         },remoteTVDetailResponse.production_countries.orEmpty().map {
             ProductionCountries(it.iso_3166_1.orEmpty(),it.name.orEmpty())
         },remoteTVDetailResponse.seasons.orEmpty().map {
             Seasons(it.air_date.orEmpty(),it.episode_count.orDefault(),it.id,it.name.orEmpty(),it.overview.orEmpty(),it.poster_path.orNa(),
             it.season_number.orEmpty(),it.vote_average.orDefault())
         },remoteTVDetailResponse.spoken_languages.orEmpty().map {
             Language(it.english_name.orEmpty(),it.iso_639_1.orEmpty(),it.name.orEmpty())
         },
         remoteTVDetailResponse.status.orEmpty(),remoteTVDetailResponse.tagline.orEmpty(),remoteTVDetailResponse.type.orEmpty(),
         remoteTVDetailResponse.vote_average.orDefault(),remoteTVDetailResponse.vote_count.orDefault())
 }

    fun mapSeasonDetailFromRemote(remoteSeasonData: RemoteSeasonData):SeasonDeatilData{
        return SeasonDeatilData(remoteSeasonData.seasonId.orEmpty(),remoteSeasonData.airDate.orEmpty(),
        remoteSeasonData.episodes.orEmpty().map {
            Episode(it.airDate.orEmpty(),it.episodeNumber.orDefault(),it.episodeType.orEmpty(),it.id,it.name.orEmpty(),it.overview.orEmpty(),
            it.productionCode.orEmpty(),it.runtime.orDefault(),it.seasonNumber.orDefault(),it.showId.orDefault(),it.stillPath.orNa(),it.voteAverage.orDefault(),
            it.voteCount.orDefault(),it.guestStars.orEmpty().map {
                GuestStar(it.character.orEmpty(),it.creditId.orEmpty(),it.order.orDefault(),it.adult.orFalse(),it.gender.orDefault(),it.id,
                it.knownForDepartment.orEmpty(),it.name.orEmpty(),it.originalName.orEmpty(),it.popularity.orDefault(),it.profilePath.orEmpty())
                })
        },remoteSeasonData.name.orEmpty(),remoteSeasonData.overview.orEmpty(),remoteSeasonData.id,remoteSeasonData.posterPath.orEmpty(),
        remoteSeasonData.seasonNumber.orDefault(),remoteSeasonData.voteAverage.orDefault())
    }
}