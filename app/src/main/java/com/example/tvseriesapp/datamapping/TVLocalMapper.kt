package com.example.tvseriesapp.datamapping

import com.example.tvseriesapp.extensions.orDefault
import com.example.tvseriesapp.extensions.orFalse
import com.example.tvseriesapp.extensions.orNa
import com.example.tvseriesapp.model.TVData
import com.example.tvseriesapp.roomdatabase.TVDBModel
import com.example.tvseriesapp.roomdatabase.TVSeriesDBModel

class TVLocalMapper {

    fun mapToLocal(tvData: TVData):TVDBModel{
        return TVDBModel(tvData.adult,tvData.backdrop_path,tvData.id,/*tvData.origin_country,*/tvData.original_language,tvData.original_name,tvData.overview,tvData.popularity,
        tvData.poster_path,tvData.first_air_date,tvData.name,tvData.vote_average,tvData.vote_count)
    }

    fun mapFromLocal(tvdbModel: TVDBModel):TVData{
        return TVData(tvdbModel.adult.orFalse(),tvdbModel.backdrop_path.orEmpty(),tvdbModel.id,/*tvdbModel.origin_country.orEmpty(),*/tvdbModel.original_language.orEmpty(),tvdbModel.original_name.orEmpty(),tvdbModel.overview.orEmpty(),tvdbModel.popularity.orDefault(),
            tvdbModel.poster_path.orEmpty(),tvdbModel.first_air_date.orEmpty(),tvdbModel.name.orEmpty(),tvdbModel.vote_average.orDefault(),tvdbModel.vote_count.orDefault())
    }

    fun mapAirTodayToLocal(tvData: TVData):TVSeriesDBModel{
        return TVSeriesDBModel(tvData.adult.orFalse(),tvData.backdrop_path.orEmpty(),tvData.id,/*tvdbModel.origin_country.orEmpty(),*/tvData.original_language.orEmpty(),tvData.original_name.orEmpty(),tvData.overview.orEmpty(),tvData.popularity.orDefault(),
            tvData.poster_path.orEmpty(),tvData.first_air_date.orEmpty(),tvData.name.orEmpty(),tvData.vote_average.orDefault(),tvData.vote_count.orDefault())
    }
    fun mapAirTodayFromLocal(tvSeriesDBModel: TVSeriesDBModel):TVData{
        return TVData(tvSeriesDBModel.adult.orFalse(),tvSeriesDBModel.backdrop_path.orEmpty(),tvSeriesDBModel.id,/*tvdbModel.origin_country.orEmpty(),*/tvSeriesDBModel.original_language.orEmpty(),tvSeriesDBModel.original_name.orEmpty(),tvSeriesDBModel.overview.orEmpty(),tvSeriesDBModel.popularity.orDefault(),
            tvSeriesDBModel.poster_path.orEmpty(),tvSeriesDBModel.first_air_date.orEmpty(),tvSeriesDBModel.name.orEmpty(),tvSeriesDBModel.vote_average.orDefault(),tvSeriesDBModel.vote_count.orDefault())
    }
}