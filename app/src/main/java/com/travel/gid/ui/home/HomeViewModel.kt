package com.travel.gid.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.travel.gid.data.Resource
import com.travel.gid.data.models.Direction
import com.travel.gid.data.models.Tour
import com.travel.gid.domain.usecases.GetHomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeUseCase: GetHomeUseCase
): ViewModel() {

    val directionsLiveDataPrivate = MutableLiveData<Response<Direction>>()
    val directionsLiveData : LiveData<Response<Direction>> get() = directionsLiveDataPrivate

    val tourLiveDataPrivate = MutableLiveData<Response<Tour>>()
    val tourLiveData : LiveData<Response<Tour>> get() = tourLiveDataPrivate

//    var tour: Tour? = null
   // var directions: Direction? = null

    init {
        viewModelScope.launch {
            directionsLiveDataPrivate.postValue(getHomeUseCase.getDirection())

            tourLiveDataPrivate.postValue(getHomeUseCase.getTours())
        }
    }

    suspend fun getTour(): Response<Tour>  = getHomeUseCase.getTours()

    fun getDirections(){

    }

}