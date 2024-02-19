package com.example.tvseriesapp.usecase

import com.example.tvseriesapp.model.Cast
import com.example.tvseriesapp.model.RemoteCreditResponse
import com.example.tvseriesapp.packagerepository.TVRemoteRepository
import io.reactivex.Observable
import io.reactivex.Single

class CastUseCase(private val remoteRepository: TVRemoteRepository) {
   fun execute(id:String):Single<RemoteCreditResponse>{
       return remoteRepository.getTVCredits(id)
   }
}