package com.travel.gid.domain.usecases

import com.travel.gid.domain.repository.HomeRepository
import javax.inject.Inject

class GetHomeUseCase @Inject constructor(private val repo: HomeRepository) {
    suspend fun getTours() = repo.getTours()
    suspend fun getTour() = repo.getTour()
    suspend fun getDirections() = repo.getDirections()
}