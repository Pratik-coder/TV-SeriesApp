package com.example.tvseriesapp.packagerepository

import com.example.tvseriesapp.model.*
import io.reactivex.Single

interface TVRemoteRepository {

  fun getAiringTodayTV(page:Int):Single<MainTVData>
  fun getOnTheAirShow(page: Int):Single<MainTVData>
  fun getTVShowsBySearch(strQuery:String,page: Int):Single<MainTVData>
  fun getTVSeriesDetails(id:String):Single<TVDetailData>
  fun getTVSeasonDetails(seriesId:String,seasonNumber:String):Single<SeasonDeatilData>
  fun getTVCredits(id:String):Single<RemoteCreditResponse>
  fun getCastDetailData(id:Int):Single<RemoteCastDetailData>
  fun getVideos(id:String):Single<RemoteVideoResponse>
  fun getPersonImages(id:Int):Single<RemotePersonImages>
  fun getAllPersonCredite(id:Int):Single<RemoteAllCreditResponse>
}