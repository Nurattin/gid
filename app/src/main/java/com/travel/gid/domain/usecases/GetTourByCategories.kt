package com.travel.gid.domain.usecases

import com.travel.gid.data.models.Tour
import com.travel.gid.domain.repository.HomeRepository
import com.travel.gid.domain.repository.TourByCategoriesRepository
import retrofit2.Response
import javax.inject.Inject

class GetTourByCategories @Inject constructor(private val repo: TourByCategoriesRepository) {
    suspend fun getToursByCategories(id: Long): Response<Tour> = repo.getToursByCategories(id)

}