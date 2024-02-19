package com.example.tvseriesapp.usecase

import com.example.tvseriesapp.model.RemoteAllCreditResponse
import com.example.tvseriesapp.packagerepository.TVRemoteRepository
import io.reactivex.Single

class AllCreditUseCase(private val tvRemoteRepository: TVRemoteRepository) {
    fun execute(id:Int):Single<RemoteAllCreditResponse>{
        return tvRemoteRepository.getAllPersonCredite(id)
    }
}