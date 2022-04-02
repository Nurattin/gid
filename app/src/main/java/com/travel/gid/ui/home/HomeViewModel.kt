package com.travel.gid.ui.home

import androidx.databinding.ObservableField
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

    val tourLiveDataPrivate = MutableLiveData<Resource<Direction>>()
    val tourLiveData : LiveData<Resource<Direction>> get() = tourLiveDataPrivate

    var tour: Tour? = null
    var directions: Direction? = null

    suspend fun getTour(): Response<Tour>  = getHomeUseCase.getTours()

    fun getDirections(){
            viewModelScope.launch {
                getHomeUseCase.getTour().collect {
                    directions = it.data
                    tourLiveDataPrivate.value = it
                }
            }
    }

}