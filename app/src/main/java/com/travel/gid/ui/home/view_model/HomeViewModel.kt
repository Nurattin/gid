package com.travel.gid.ui.home.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.travel.gid.data.models.Direction
import com.travel.gid.data.models.DirectionData
import com.travel.gid.data.models.Tour
import com.travel.gid.domain.usecases.GetHomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(

) : ViewModel() {

    var tour: Tour? = null

    private val _directionList = MutableLiveData<Response<Direction>>()
    val directionList: LiveData<Response<Direction>>
        get() = _directionList

    var currentItem = 0
    var lastItem = 0



//    suspend fun getTour(): Response<Tour> = getHomeUseCase.getTours()



}