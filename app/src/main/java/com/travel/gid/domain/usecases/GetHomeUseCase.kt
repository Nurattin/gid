package com.travel.gid.domain.usecases

import com.travel.gid.data.Resource
import com.travel.gid.data.models.Direction
import com.travel.gid.data.models.Tour
import com.travel.gid.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class GetHomeUseCase @Inject constructor(private val repo: HomeRepository) {
    suspend fun getTours(): Response<Tour> = repo.getTours()
    suspend fun getDirection(): Response<Direction> = repo.getDirection()
    suspend fun getDirections(): Response<Direction> = repo.getDirections()
}