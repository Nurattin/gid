package com.travel.gid.domain.usecases

import com.travel.gid.data.models.Tour
import com.travel.gid.domain.repository.TourListFilterRepository
import retrofit2.Response
import javax.inject.Inject

class GetTourListFilter @Inject constructor(private val repo: TourListFilterRepository) {
    suspend fun getTourListFilter(priceFrom: Int? = null, priceTo: Int? = null,categories: Array<Int>? = null):
            Response<Tour> = repo.getTourListFilter(priceFrom, priceTo, categories)
}