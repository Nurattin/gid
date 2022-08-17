package com.travel.gid.domain.usecases

import com.travel.gid.data.models.Filters
import com.travel.gid.data.models.Direction
import com.travel.gid.data.models.Tour
import com.travel.gid.domain.repository.HomeRepository
import retrofit2.Response
import javax.inject.Inject

class GetHomeUseCase @Inject constructor(private val repo: HomeRepository) {
    suspend fun getTours(): Response<Tour> = repo.getTours()
}