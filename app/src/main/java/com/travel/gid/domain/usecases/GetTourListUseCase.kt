package com.travel.gid.domain.usecases

import com.travel.gid.data.models.Tour
import com.travel.gid.domain.repository.TourListRepository
import retrofit2.Response
import javax.inject.Inject

class GetTourListUseCase @Inject constructor(private val repo: TourListRepository) {
    suspend fun getTourListFilter(
        priceFrom: Int?,
        priceTo: Int?,
        categories: List<Int>?,
        orderByPrice: String?,
    ):
            Response<Tour> = repo.getTourList(
        priceFrom = priceFrom,
        priceTo = priceTo,
        categories = categories,
        orderByPrice = orderByPrice
    )
}