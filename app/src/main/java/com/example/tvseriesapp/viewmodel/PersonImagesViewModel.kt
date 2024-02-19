package com.example.tvseriesapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tvseriesapp.model.PersonProfile
import com.example.tvseriesapp.status.Resource
import com.example.tvseriesapp.usecase.PersonImagesUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PersonImagesViewModel(private val personImagesUseCase: PersonImagesUseCase):ViewModel() {

    private val stateFlow=MutableStateFlow<Resource<List<PersonProfile>>>(Resource.Empty())
    val personImageList:StateFlow<Resource<List<PersonProfile>>>
    get() = stateFlow
    var disposasble:Disposable?=null

    fun getPersonImages(id:Int){
        stateFlow.value= Resource.Loading()
        disposasble=personImagesUseCase.execute(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                res->
                stateFlow.value=Resource.Success(res.profiles)
            },
                {
                    throwable->
                    throwable.localizedMessage?.let {
                        stateFlow.value= Resource.Error(it)
                    }
                })
    }
}