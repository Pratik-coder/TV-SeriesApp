package com.example.tvseriesapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tvseriesapp.model.TVData
import com.example.tvseriesapp.status.Resource
import com.example.tvseriesapp.usecase.GetFavouriteMoviesUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FavouriteViewModel(private val getFavouriteMoviesUseCase: GetFavouriteMoviesUseCase):ViewModel() {

    private val stateFlow=MutableStateFlow<Resource<List<TVData>>>(Resource.Loading())
    val favouriteTvList:StateFlow<Resource<List<TVData>>>
    get()=stateFlow

    var disposable:Disposable?=null

    fun fetchFavouriteMovies(){
        stateFlow.value=Resource.Loading()
        disposable=getFavouriteMoviesUseCase.execute()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({res->
                stateFlow.value=Resource.Success(res)
            },{
                throwable->
                 throwable.localizedMessage?.let {
                     stateFlow.value=Resource.Error(it)
                 }
            })
    }
}