package com.example.tvseriesapp.roomdatabase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import io.reactivex.Completable
import io.reactivex.CompletableEmitter
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface TVDao {

    @Query("SELECT * FROM favourite_series")
    fun getFavouriteTvSeries():Observable<List<TVDBModel>>

    @Query("SELECT * FROM favourite_series where id ==:id")
    fun getFavouriteTVSeriesById(id:Int):Single<TVDBModel>

    @Insert
    fun addFavouriteTv(tvdbModel: TVDBModel):Completable

    @Delete
    fun removeFavouriteTv(tvdbModel: TVDBModel):Completable

    @Update
    fun updateFavourites(tvdbModel: TVDBModel):Completable

    @Query("SELECT * FROM tv_shows")
    fun getAirTvSeries():Observable<List<TVSeriesDBModel>>
}