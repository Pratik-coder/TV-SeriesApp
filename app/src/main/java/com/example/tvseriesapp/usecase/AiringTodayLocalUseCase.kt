package com.example.tvseriesapp.usecase

import com.example.tvseriesapp.localdb.LocalRepository
import com.example.tvseriesapp.model.TVData
import io.reactivex.Observable

class AiringTodayLocalUseCase(private val localRepository: LocalRepository) {
    fun execute():Observable<List<TVData>>{
        return localRepository.getOnAirTodaySeries()
    }
}