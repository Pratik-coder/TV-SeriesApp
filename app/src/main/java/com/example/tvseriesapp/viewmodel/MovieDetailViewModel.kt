package com.example.tvseriesapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tvseriesapp.model.*
import com.example.tvseriesapp.status.Resource
import com.example.tvseriesapp.usecase.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MovieDetailViewModel(private val movieDetailUseCase: MovieDetailUseCase,
private val addFavouriteTVUseCase: AddFavouriteTVUseCase,
private val deleteFavouriteUseCase: DeleteFavouriteUseCase,
private val getFavouriteTVUseCase: GetFavouriteTVUseCase,
private val upDateFavouriteTVUseCase: UpDateFavouriteUseCase,
private val castUseCase: CastUseCase,
private val videoListUseCase: VideoListUseCase):ViewModel() {

    private val stateFlow=MutableStateFlow<Resource<TVDetailData>>(Resource.Empty())
    val tvDetails:StateFlow<Resource<TVDetailData>>
    get() = stateFlow

    private val favouriteTVSateFlow=MutableStateFlow<Resource<Boolean>>(Resource.Empty())
    val favouriteTv:StateFlow<Resource<Boolean>>
    get() = favouriteTVSateFlow

    private val castStateFlow= MutableStateFlow<Resource<List<Cast>>>(Resource.Empty())
    val castFlow:StateFlow<Resource<List<Cast>>>
    get() = castStateFlow

    private val videoStateFlow=MutableStateFlow<Resource<List<Videos>>>(Resource.Empty())
    val videoList:StateFlow<Resource<List<Videos>>>
        get() = videoStateFlow

    var disposable:Disposable?=null

    fun getTVShowDetail(id:String){
        stateFlow.value=Resource.Loading()
        disposable=movieDetailUseCase.execute(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                res->
                stateFlow.value=Resource.Success(res)
            },
                {
                    throwable->
                    throwable.localizedMessage?.let {
                        stateFlow.value=Resource.Error(it)
                    }
                })
    }

    fun addToFavourite(tvDetailData: TVDetailData){
        favouriteTVSateFlow.value= Resource.Loading()
        disposable=addFavouriteTVUseCase.execute(tvDetailData.toSimple())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                favouriteTVSateFlow.value=Resource.Success(true)
            },
                {
                    throwable->
                    throwable.localizedMessage?.let {
                        favouriteTVSateFlow.value= Resource.Error(it)
                    }
                })
    }

   fun deleteFromFavourite(tvDetailData: TVDetailData){
       favouriteTVSateFlow.value=Resource.Loading()
       disposable=deleteFavouriteUseCase.execute(tvDetailData.toSimple())
           .subscribeOn(Schedulers.io())
           .observeOn(AndroidSchedulers.mainThread())
           .subscribe({
               favouriteTVSateFlow.value=Resource.Success(false)
           },{
               throwable->
                  throwable.localizedMessage?.let {
                      favouriteTVSateFlow.value=Resource.Error(it)
                  }
           })
   }

    fun upDateFavourite(tvDetailData: TVDetailData){
        favouriteTVSateFlow.value=Resource.Loading()
        disposable=upDateFavouriteTVUseCase.execute(tvDetailData.toSimple())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                favouriteTVSateFlow.value= Resource.Success(true)
            },{
                throwable->
                throwable.localizedMessage?.let {
                    favouriteTVSateFlow.value= Resource.Error(it)
                }
            })
    }

    fun toggleFavourite(tvDetailData: TVDetailData){
        favouriteTVSateFlow.value= Resource.Loading()
        disposable=getFavouriteTVUseCase.execute(tvDetailData.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                deleteFromFavourite(tvDetailData)
            },{
                throwable->
                addToFavourite(tvDetailData)
            })
    }

    fun getFavouriteMovie(tvDetailData: TVDetailData){
        favouriteTVSateFlow.value= Resource.Loading()
        disposable=getFavouriteTVUseCase.execute(tvDetailData.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                upDateFavourite(tvDetailData)
                favouriteTVSateFlow.value=Resource.Success(true)
            },{
                throwable->
                favouriteTVSateFlow.value= Resource.Success(false)
            })
    }

    fun getCastList(id:String){
        castStateFlow.value= Resource.Loading()
        disposable=castUseCase.execute(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                res->
                castStateFlow.value=Resource.Success(res.cast)
            },
                {
                    throwable->
                    throwable.localizedMessage?.let {
                        castStateFlow.value= Resource.Error(it)
                    }
                }
            )
    }

    fun getVideoList(id:String){
        videoStateFlow.value= Resource.Loading()
        disposable=videoListUseCase.execute(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                    res->
                videoStateFlow.value= Resource.Success(res.results)
            },
                {
                        throwable->
                    throwable.localizedMessage?.let {
                        videoStateFlow.value= Resource.Error(it)
                    }
                })
    }

}