package com.travel.gid.domain.repository

import com.travel.gid.data.models.Tour
import com.travel.gid.data.models.TourDetail
import retrofit2.Response

interface TourListRepository {
    suspend fun getTourList(priceFrom: Int?, priceTo: Int?, categories: List<Int>?, orderByPrice: String?,): Response<Tour>
}