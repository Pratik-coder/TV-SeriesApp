package com.example.tvseriesapp.api

import com.example.tvseriesapp.model.*
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
 @GET("tv/airing_today")
 fun getAirTodayTVList(@Query("page") page:Int):Single<RemoteTVResponse>

 @GET("tv/on_the_air")
 fun getOnTheAirTVShow(@Query("page")page: Int):Single<RemoteTVResponse>

 @GET("search/tv")
 fun getTVBySearch(@Query("query")query:String,@Query("page")page: Int):Single<RemoteTVResponse>

 @GET("tv/{id}")
 fun getTVSeriesDetails(@Path("id")id:String):Single<RemoteTVDetailResponse>

 @GET("tv/{id}/season/{season_number}")
 fun getTVSeriesSeasonDetails(@Path("id")seriesId:String,@Path("season_number")seasonNumber:String):Single<RemoteSeasonData>

 @GET("tv/{id}/credits")
 fun getTVCredits(@Path("id")id:String):Single<RemoteCreditResponse>

 @GET("tv/{id}/videos")
 fun getVideos(@Path("id")id:String):Single<RemoteVideoResponse>

 @GET("person/{id}")
 fun getCastDetails(@Path("id")id:Int):Single<RemoteCastDetailData>

 @GET("person/{id}/images")
 fun getPersonImages(@Path ("id")id:Int):Single<RemotePersonImages>

 @GET("person/{id}/combined_credits")
  fun getAllCredits(@Path("id") id:Int):Single<RemoteAllCreditResponse>
}