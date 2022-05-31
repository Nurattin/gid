package com.travel.gid.ui.tour_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.travel.gid.data.models.Direction
import com.travel.gid.data.models.TourDetail
import com.travel.gid.domain.usecases.GetTourDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class ToutDetailViewModel @Inject constructor(var repository: GetTourDetailUseCase) : ViewModel() {


    val tourDetail = MutableLiveData<TourDetail?>()


    suspend fun getTourDetail(id: Long) {
        tourDetail.value = repository.getTourDetail(id).body()
    }

}