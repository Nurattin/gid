package com.travel.gid.ui.map_detail.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.travel.gid.data.models.Place
import com.travel.gid.domain.usecases.GetPlaceByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(private val getPlaceByIdUseCase: GetPlaceByIdUseCase): ViewModel() {

    private val _place = MutableLiveData<Response<Place>>()
    val place: LiveData<Response<Place>>
        get() = _place


    fun getPlace(id: Long) {
        viewModelScope.launch {
            _place.value = getPlaceByIdUseCase.getPlaceById(id)
        }
    }
}