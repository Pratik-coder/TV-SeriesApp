package com.example.tvseriesapp.localdb

import com.example.tvseriesapp.model.TVData
import com.example.tvseriesapp.roomdatabase.TVDBModel
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface LocalRepository {

    fun getFavouriteMovies():Observable<List<TVData>>

    fun getFavouriteMovieById(id:Int):Single<TVData>

    fun addTVtoFavourite(tvData: TVData):Completable

    fun deleteFromFavourite(tvData: TVData):Completable

    fun updateFavourites(tvData: TVData):Completable

    fun getOnAirTodaySeries():Observable<List<TVData>>
}