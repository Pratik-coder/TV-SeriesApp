package com.example.tvseriesapp.localdb

import android.content.Context
import com.example.tvseriesapp.datamapping.TVLocalMapper
import com.example.tvseriesapp.model.TVData
import com.example.tvseriesapp.roomdatabase.TVDao
import com.example.tvseriesapp.roomdatabase.TVSeriesDatabase
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

class LocalRepositoryImplementation(private val context:Context,private val localMapper: TVLocalMapper):LocalRepository {
    override fun getFavouriteMovies(): Observable<List<TVData>> {
        return TVSeriesDatabase.invoke(context).tvDao().getFavouriteTvSeries().map {
            it.map {
                localMapper.mapFromLocal(it)
            }
        }
    }

    override fun getFavouriteMovieById(id: Int): Single<TVData> {
           return TVSeriesDatabase.invoke(context).tvDao().getFavouriteTVSeriesById(id).map {
               localMapper.mapFromLocal(it)
           }
    }

    override fun addTVtoFavourite(tvData: TVData): Completable {
       return TVSeriesDatabase.invoke(context).tvDao().addFavouriteTv(localMapper.mapToLocal(tvData))
    }

    override fun deleteFromFavourite(tvData: TVData): Completable {
       return TVSeriesDatabase.invoke(context).tvDao().removeFavouriteTv(localMapper.mapToLocal(tvData))
    }

    override fun updateFavourites(tvData: TVData): Completable {
       return TVSeriesDatabase.invoke(context).tvDao().updateFavourites(localMapper.mapToLocal(tvData))
    }

    override fun getOnAirTodaySeries(): Observable<List<TVData>> {
        return TVSeriesDatabase.invoke(context).tvDao().getAirTvSeries().map {
            it.map {
                localMapper.mapAirTodayFromLocal(it)
            }
        }
    }
}