package com.example.tvseriesapp.usecase

import com.example.tvseriesapp.localdb.LocalRepository
import com.example.tvseriesapp.model.TVData
import io.reactivex.Completable

class UpDateFavouriteUseCase(private val localRepository: LocalRepository) {
    fun execute(tvData: TVData):Completable{
        return localRepository.updateFavourites(tvData)
    }
}