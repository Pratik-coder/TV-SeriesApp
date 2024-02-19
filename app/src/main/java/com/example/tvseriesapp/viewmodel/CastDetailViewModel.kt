package com.example.tvseriesapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tvseriesapp.model.PersonCast
import com.example.tvseriesapp.model.PersonProfile
import com.example.tvseriesapp.model.RemoteCastDetailData
import com.example.tvseriesapp.status.Resource
import com.example.tvseriesapp.usecase.AllCreditUseCase
import com.example.tvseriesapp.usecase.CastDetailUseCase
import com.example.tvseriesapp.usecase.PersonImagesUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CastDetailViewModel(private val castDetailUseCase: CastDetailUseCase,
                          private val personImagesUseCase: PersonImagesUseCase,
                          private val allCreditUseCase: AllCreditUseCase
): ViewModel() {

    private val stateFlow= MutableStateFlow<Resource<RemoteCastDetailData>>(Resource.Empty())
    val castDetailData:StateFlow<Resource<RemoteCastDetailData>>
    get() = stateFlow

    private val imageStateFlow=MutableStateFlow<Resource<List<PersonProfile>>>(Resource.Empty())
    val personImageList:StateFlow<Resource<List<PersonProfile>>>
        get() = imageStateFlow

    private val allCreditStateFlow=MutableStateFlow<Resource<List<PersonCast>>>(Resource.Empty())
    val creditList:StateFlow<Resource<List<PersonCast>>>
    get() = allCreditStateFlow
    var disposable:Disposable?=null

    fun getCastDetails(id:Int){
        stateFlow.value=Resource.Loading()
        disposable=castDetailUseCase.execute(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                res->
                stateFlow.value=Resource.Success(res)
            },{
                throwable->
                throwable.localizedMessage?.let {
                    stateFlow.value=Resource.Error(it)
                }
            }
            )
    }

    fun getPersonImages(id:Int){
        imageStateFlow.value= Resource.Loading()
        disposable=personImagesUseCase.execute(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                    res->
                imageStateFlow.value=Resource.Success(res.profiles)
            },
                {
                        throwable->
                    throwable.localizedMessage?.let {
                        stateFlow.value= Resource.Error(it)
                    }
                })
    }

    fun getAllCredits(id:Int){
        allCreditStateFlow.value= Resource.Loading()
        disposable=allCreditUseCase.execute(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                res->
                allCreditStateFlow.value= Resource.Success(res.cast)
            },
                {
                    throwable->
                    throwable.localizedMessage?.let {
                        allCreditStateFlow.value= Resource.Error(it)
                    }
                })
    }
}