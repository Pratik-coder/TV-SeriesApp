package com.example.tvseriesapp.usecase

import com.example.tvseriesapp.model.TVDetailData
import com.example.tvseriesapp.packagerepository.TVRemoteRepository
import io.reactivex.Single

class MovieDetailUseCase(private val tvRemoteRepository: TVRemoteRepository) {
    fun execute(id:String):Single<TVDetailData>{
        return tvRemoteRepository.getTVSeriesDetails(id)
    }
}