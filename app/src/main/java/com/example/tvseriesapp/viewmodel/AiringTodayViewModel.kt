package com.example.tvseriesapp.viewmodel

import androidx.annotation.MainThread
import androidx.lifecycle.ViewModel
import com.example.tvseriesapp.model.TVData
import com.example.tvseriesapp.status.Resource
import com.example.tvseriesapp.usecase.AiringTodayLocalUseCase
import com.example.tvseriesapp.usecase.AiringTodayUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AiringTodayViewModel(private val airingTodayUseCase: AiringTodayUseCase,private val airingTodayLocalUseCase: AiringTodayLocalUseCase):ViewModel() {

    private val airingTv= MutableStateFlow<Resource<List<TVData>>>(Resource.Empty())
    private var currentPage=1
    private var lastPage=1
    var disposable:Disposable?=null

    val airingTVShow:StateFlow<Resource<List<TVData>>>
    get() = airingTv

    private val localAiringTv=MutableStateFlow<Resource<List<TVData>>>(Resource.Empty())
    val localAiringTvShow:StateFlow<Resource<List<TVData>>>
    get() = localAiringTv

    fun getAiringTVMovies(){
        airingTv.value= Resource.Loading()
        disposable=airingTodayUseCase.execute(currentPage)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({ res->
                lastPage=res.total_pages
                airingTv.value= Resource.Success(res.results)
            },
                {
                    throwable->
                    lastPage=currentPage
                    throwable.localizedMessage?.let {
                        airingTv.value= Resource.Error(it)
                    }
                })
    }

    fun getAiringTvSeriesLocally(){
        localAiringTv.value= Resource.Loading()
        disposable=airingTodayLocalUseCase.execute()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                res->
                localAiringTv.value= Resource.Success(res)
            },
                {
                    throwable->
                    throwable.localizedMessage?.let {
                        localAiringTv.value= Resource.Error(it)
                    }
                })
    }

    fun fetchNextAiringTV(){
        currentPage++
        getAiringTVMovies()
    }

    fun refreshAiringTV(){
        currentPage=1
        getAiringTVMovies()
    }

    fun isFirstPage():Boolean{
        return currentPage==1
    }

    fun isLastPage():Boolean{
        return currentPage==lastPage
    }
}