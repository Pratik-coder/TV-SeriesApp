package com.example.tvseriesapp.usecase

import com.example.tvseriesapp.localdb.LocalRepository
import com.example.tvseriesapp.model.TVData
import io.reactivex.Completable

class AddFavouriteTVUseCase(private val localRepository: LocalRepository) {
    fun execute(tvData: TVData):Completable{
        return localRepository.addTVtoFavourite(tvData)
    }
}