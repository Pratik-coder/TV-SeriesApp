package com.example.tvseriesapp.usecase

import com.example.tvseriesapp.model.MainTVData
import com.example.tvseriesapp.packagerepository.TVRemoteRepository
import io.reactivex.Single

class SearchMovieUseCase(private val tvRemoteRepository: TVRemoteRepository) {
    fun execute(strQuery:String,page:Int):Single<MainTVData>{
        return tvRemoteRepository.getTVShowsBySearch(strQuery,page)
    }
}