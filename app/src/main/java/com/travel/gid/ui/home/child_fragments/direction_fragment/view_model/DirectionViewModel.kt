package com.travel.gid.ui.home.child_fragments.direction_fragment.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.travel.gid.data.models.Direction
import com.travel.gid.data.models.Tour
import com.travel.gid.domain.usecases.GetHomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class DirectionViewModel @Inject constructor(private val getHomeUseCase: GetHomeUseCase) :
    ViewModel() {

    private val _directionsList = MutableLiveData<Response<Direction>>()
    val directionsList: LiveData<Response<Direction>> get() = _directionsList

    private val _tourList = MutableLiveData<Response<Tour>>()
    val tourList: LiveData<Response<Tour>> get() = _tourList

    init {
        viewModelScope.launch {
            _directionsList.value = getHomeUseCase.getDirection()
        }
        viewModelScope.launch {
            _tourList.value = getHomeUseCase.getTours()
        }
    }

    fun getDirectionList() {
        viewModelScope.launch {
            _directionsList.value = getHomeUseCase.getDirection()
        }
    }
}