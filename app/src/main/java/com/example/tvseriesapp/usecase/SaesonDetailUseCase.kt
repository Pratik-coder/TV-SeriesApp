package com.example.tvseriesapp.usecase

import com.example.tvseriesapp.model.SeasonDeatilData
import com.example.tvseriesapp.packagerepository.TVRemoteRepository
import io.reactivex.Single

class SaesonDetailUseCase(private val repository: TVRemoteRepository) {
    fun execute(seriesId:String,sesasonNumber:String):Single<SeasonDeatilData>{
        return repository.getTVSeasonDetails(seriesId,sesasonNumber)
    }
}