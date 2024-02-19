package com.example.tvseriesapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.tvseriesapp.model.TVData
import com.example.tvseriesapp.status.Resource
import com.example.tvseriesapp.usecase.SearchMovieUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SearchViewModel(private val searchMovieUseCase: SearchMovieUseCase):ViewModel() {

    private val stateFlow=MutableStateFlow<Resource<List<TVData>>>(Resource.Empty())
    private var currentPage=1
    private var lastPage=1

    val searchTVList:StateFlow<Resource<List<TVData>>>
    get()=stateFlow

    var disposable:Disposable?=null

    fun fetchSearchTVShows(strQuery:String){
        stateFlow.value=Resource.Loading()
        disposable?.dispose()
        disposable=searchMovieUseCase.execute(strQuery,currentPage)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({
            res->
            lastPage=res.total_pages
            stateFlow.value=Resource.Success(res.results)
        },{
            throwable->
            lastPage=currentPage
            throwable.localizedMessage?.let {
                stateFlow.value=Resource.Error(it)
            }
        })

    }

    fun refreshSearchTV(strQuery:String){
        currentPage=1
        fetchSearchTVShows(strQuery)
    }

    fun fetchNextSearchTV(strQuery: String){
        currentPage++
        fetchSearchTVShows(strQuery)
    }

    fun isFirstPage():Boolean{
        return currentPage==1
    }

    fun isLastPage():Boolean{
        return currentPage==lastPage
    }
}