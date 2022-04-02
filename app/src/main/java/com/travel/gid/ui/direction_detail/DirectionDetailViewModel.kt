package com.travel.gid.ui.direction_detail

import androidx.lifecycle.ViewModel
import com.travel.gid.data.models.DirectionDetail
import com.travel.gid.domain.usecases.GetDirectionDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DirectionDetailViewModel  @Inject constructor(
    private val getHomeUseCase: GetDirectionDetailUseCase
): ViewModel() {

    var directionDetail : DirectionDetail? = null

    suspend fun getDirectionDetail(id: Long) = getHomeUseCase.getDirectionDetail(id)

}