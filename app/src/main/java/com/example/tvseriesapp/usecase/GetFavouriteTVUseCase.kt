package com.example.tvseriesapp.usecase

import com.example.tvseriesapp.localdb.LocalRepository
import com.example.tvseriesapp.model.TVData
import io.reactivex.Single

class GetFavouriteTVUseCase(private val localRepository: LocalRepository) {
    fun execute(id:Int):Single<TVData>{
        return localRepository.getFavouriteMovieById(id)
    }
}