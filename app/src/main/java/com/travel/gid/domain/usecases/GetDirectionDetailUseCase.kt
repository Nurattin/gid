package com.travel.gid.domain.usecases

import com.travel.gid.domain.repository.DirectionDetailRepository
import javax.inject.Inject

class GetDirectionDetailUseCase @Inject constructor(private val repo: DirectionDetailRepository) {
    suspend fun getDirectionDetail(id: Long) = repo.getDirectionDetail(id)
}