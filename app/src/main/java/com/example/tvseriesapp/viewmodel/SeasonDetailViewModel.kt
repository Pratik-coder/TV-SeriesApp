package com.example.tvseriesapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tvseriesapp.model.SeasonDeatilData
import com.example.tvseriesapp.status.Resource
import com.example.tvseriesapp.usecase.SaesonDetailUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SeasonDetailViewModel(private val saesonDetailUseCase: SaesonDetailUseCase):ViewModel() {

    private val stateFlow=MutableStateFlow<Resource<SeasonDeatilData>>(Resource.Empty())
    val seasonData:StateFlow<Resource<SeasonDeatilData>>
    get()=stateFlow

    private var currentPage=1
    private var lastPage=1
    var dispsosable:Disposable?=null

    fun fetchSeasonDetail(seriesId:String,seasonNumber:String){
        stateFlow.value= Resource.Loading()
        dispsosable=saesonDetailUseCase.execute(seriesId,seasonNumber)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                res->
                 stateFlow.value= Resource.Success(res)
            },
                {
                    throwable->
                    throwable.localizedMessage?.let {
                        stateFlow.value=Resource.Error(it)
                    }
                })

    }

    fun refreshSeasonDetailWithEpisodeList(seriesId:String,seasonNumber:String){
             currentPage=1
           fetchSeasonDetail(seriesId,seasonNumber)
    }

    fun fetchNextEpisodeListWithSeasonDetail(seriesId:String,seasonNumber:String){
        currentPage++
        fetchSeasonDetail(seriesId,seasonNumber)
    }

    fun isFirstPage():Boolean{
        return currentPage==1
    }

    fun isLastPage():Boolean{
        return currentPage==lastPage
    }
}