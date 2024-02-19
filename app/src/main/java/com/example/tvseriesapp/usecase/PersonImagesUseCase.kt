package com.example.tvseriesapp.usecase

import com.example.tvseriesapp.model.RemotePersonImages
import com.example.tvseriesapp.packagerepository.TVRemoteRepository
import io.reactivex.Single

class PersonImagesUseCase(private val tvRemoteRepository: TVRemoteRepository) {
    fun execute(id:Int):Single<RemotePersonImages>{
        return tvRemoteRepository.getPersonImages(id)
    }
}