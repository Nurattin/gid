package com.travel.gid.ui.tour_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.travel.gid.data.models.Places
import com.travel.gid.data.models.Tour
import com.travel.gid.data.models.TourDetail
import com.travel.gid.domain.usecases.GetTourDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class ToutDetailViewModel @Inject constructor(var repository: GetTourDetailUseCase) : ViewModel() {


    private val _toursDetail = MutableLiveData<TourDetail>()
    val toursDetail: LiveData<TourDetail>
        get() = _toursDetail


    fun getTourDetail(id: Long) {
        viewModelScope.launch {
            _toursDetail.value = repository.getTourDetail(id).body()
        }
    }

    fun getPlaces(): List<Places>? {
        return toursDetail.value?.data?.places
    }

}