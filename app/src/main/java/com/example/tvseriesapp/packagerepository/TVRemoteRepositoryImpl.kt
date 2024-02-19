package com.example.tvseriesapp.packagerepository

import com.example.tvseriesapp.datamapping.TVRemoteMapper
import com.example.tvseriesapp.model.*
import com.example.tvseriesapp.retrofit.RetrofitClient
import io.reactivex.Single

class TVRemoteRepositoryImpl(private val tvRemoteMapper: TVRemoteMapper):TVRemoteRepository {
    override fun getAiringTodayTV(page:Int): Single<MainTVData> {
            return RetrofitClient.tvService().getAirTodayTVList(page).map {
                         tvRemoteMapper.mapFromRemote(it)
            }
    }

    override fun getOnTheAirShow(page: Int): Single<MainTVData> {
        return RetrofitClient.tvService().getOnTheAirTVShow(page).map {
            tvRemoteMapper.mapFromRemote(it)
        }
    }

    override fun getTVShowsBySearch(strQuery:String,page: Int): Single<MainTVData> {
       return RetrofitClient.tvService().getTVBySearch(strQuery,page).map {
           tvRemoteMapper.mapFromRemote(it)
       }
    }

    override fun getTVSeriesDetails(id: String): Single<TVDetailData> {
        return RetrofitClient.tvService().getTVSeriesDetails(id).map {
          tvRemoteMapper.mapDetailFromRemote(it)
        }
    }

    override fun getTVSeasonDetails(
        seriesId: String,
        seasonNumber: String
    ): Single<SeasonDeatilData> {
       return RetrofitClient.tvService().getTVSeriesSeasonDetails(seriesId,seasonNumber).map {
           tvRemoteMapper.mapSeasonDetailFromRemote(it)
       }
    }

    override fun getTVCredits(id: String): Single<RemoteCreditResponse> {
       return RetrofitClient.tvService().getTVCredits(id)
    }

    override fun getCastDetailData(id: Int): Single<RemoteCastDetailData> {
        return RetrofitClient.tvService().getCastDetails(id)
    }

    override fun getVideos(id: String): Single<RemoteVideoResponse> {
       return RetrofitClient.tvService().getVideos(id)
    }

    override fun getPersonImages(id: Int): Single<RemotePersonImages> {
       return RetrofitClient.tvService().getPersonImages(id)
    }

    override fun getAllPersonCredite(id: Int): Single<RemoteAllCreditResponse> {
        return RetrofitClient.tvService().getAllCredits(id)
    }
}