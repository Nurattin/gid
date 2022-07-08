package com.travel.gid.data.repository

import com.travel.gid.data.datasource.network.GidApi
import com.travel.gid.data.models.Tour
import com.travel.gid.domain.repository.TourListRepository
import retrofit2.Response
import javax.inject.Inject

class TourListRepositoryImpl @Inject constructor(var api: GidApi) :
    TourListRepository {
    override suspend fun getTourList(
        priceFrom: Int?,
        priceTo: Int?,
        categories: List<Int>?,
        orderByPrice: String?,
    ): Response<Tour> {
        return api.getTourList(
            priceFrom = priceFrom,
            priceTo = priceTo,
            categories = if (categories.isNullOrEmpty()) null else categories.toString(),
            orderByPrice = orderByPrice
        )
    }
}