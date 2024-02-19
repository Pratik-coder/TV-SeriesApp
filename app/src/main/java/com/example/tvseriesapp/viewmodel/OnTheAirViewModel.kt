package com.example.tvseriesapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tvseriesapp.model.MainTVData
import com.example.tvseriesapp.model.TVData
import com.example.tvseriesapp.status.Resource
import com.example.tvseriesapp.usecase.OnTheAirUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class OnTheAirViewModel(private val onTheAirUseCase: OnTheAirUseCase):ViewModel() {

    private val stateFlow= MutableStateFlow<Resource<List<TVData>>>(Resource.Empty())
    private var currentPage=1
    private var lastPage=1

    val onTheAirList: StateFlow<Resource<List<TVData>>>
    get() = stateFlow

    var disposable:Disposable?=null

    fun fetchOnTheAirTVShow(){
        stateFlow.value= Resource.Loading()
        disposable=onTheAirUseCase.execute(currentPage)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                res->
                lastPage=res.total_pages
                stateFlow.value= Resource.Success(res.results)
            },{
                throwable->
                lastPage=currentPage
                throwable.localizedMessage?.let {
                    stateFlow.value= Resource.Error(it)
                }
            })
    }

    fun refershOnTheAirTVShow(){
        currentPage=1
        fetchOnTheAirTVShow()
    }

    fun fetchNextOnTheAirTVShow(){
        currentPage++
        fetchOnTheAirTVShow()
    }

    fun isFirstPage():Boolean{
        return currentPage==1
    }

    fun isLastPage():Boolean{
        return currentPage==lastPage
    }


}