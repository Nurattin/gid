package com.travel.gid.ui.tour_detail

import androidx.lifecycle.ViewModel
import com.travel.gid.data.models.TourDetail
import com.travel.gid.domain.usecases.GetTourDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ToutDetailViewModel @Inject constructor(var repository: GetTourDetailUseCase) : ViewModel() {


    var tourDetail : TourDetail? = null

    suspend fun getTourDetail(id: Long) = repository.getTourDetail(id)

}