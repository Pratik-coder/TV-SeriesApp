package com.example.tvseriesapp.retrofit

import com.example.tvseriesapp.api.ApiService
import com.example.tvseriesapp.interceptor.TVInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL="https://api.themoviedb.org/3/"
    const val POSTER_BASE_URL="https://image.tmdb.org/t/p/w500"
    const val BASE_BACKDROP_URL="https://image.tmdb.org/t/p/w780"
    const val PROFILE_PATH="https://image.tmdb.org/t/p/w185"
    const val YOUTUBE_IMAGE_URL="https://img.youtube.com/vi/"
    const val YOUTUBE_WATCH_URL="https://www.youtube.com/watch?v="


    private val retrofit:Retrofit=Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(OkHttpClient.Builder()
            .addInterceptor(TVInterceptor())
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build())
        .build()

    fun tvService():ApiService= retrofit.create(ApiService::class.java)

}