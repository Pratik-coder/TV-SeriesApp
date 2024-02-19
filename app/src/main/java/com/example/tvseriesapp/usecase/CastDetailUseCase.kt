package com.example.tvseriesapp.usecase

import com.example.tvseriesapp.model.RemoteCastDetailData
import com.example.tvseriesapp.packagerepository.TVRemoteRepository
import io.reactivex.Single

class CastDetailUseCase(private val tvRemoteRepository: TVRemoteRepository) {
    fun execute(id:Int):Single<RemoteCastDetailData>{
        return tvRemoteRepository.getCastDetailData(id)
    }
}