package com.travel.gid.domain.usecases

import com.travel.gid.domain.repository.TourDetailRepository
import javax.inject.Inject

class GetTourDetailUseCase @Inject constructor(private val repo: TourDetailRepository) {
    suspend fun getTourDetail(id: Long) = repo.getTourDetail(id)
}