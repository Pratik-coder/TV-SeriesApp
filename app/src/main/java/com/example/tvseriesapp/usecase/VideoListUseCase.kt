package com.example.tvseriesapp.usecase

import com.example.tvseriesapp.model.RemoteVideoResponse
import com.example.tvseriesapp.packagerepository.TVRemoteRepository
import io.reactivex.Single

class VideoListUseCase(private val tvRemoteRepository: TVRemoteRepository) {
    fun execute(id:String):Single<RemoteVideoResponse>{
        return tvRemoteRepository.getVideos(id)
    }
}