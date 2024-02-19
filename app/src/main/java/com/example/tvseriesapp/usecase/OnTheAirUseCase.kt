package com.example.tvseriesapp.usecase

import com.example.tvseriesapp.model.MainTVData
import com.example.tvseriesapp.packagerepository.TVRemoteRepository
import io.reactivex.Single

class OnTheAirUseCase(private val tvRemoteRepository: TVRemoteRepository) {
    fun execute(page:Int):Single<MainTVData>{
       return tvRemoteRepository.getOnTheAirShow(page)
    }
}