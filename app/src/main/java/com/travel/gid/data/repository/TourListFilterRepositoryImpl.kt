package com.travel.gid.data.repository

import com.travel.gid.data.datasource.network.GidApi
import com.travel.gid.data.models.Tour
import com.travel.gid.domain.repository.TourListFilterRepository
import retrofit2.Response
import javax.inject.Inject

class TourListFilterRepositoryImpl @Inject constructor(var api: GidApi) :
TourListFilterRepository  {
    override suspend fun getTourListFilter(
        priceFrom: Int?,
        priceTo: Int?,
        categories: List<Int>?
    ): Response<Tour> {
       return api.getToursListFilter(priceFrom = priceFrom, priceTo = priceTo, categories = if (categories!!.isEmpty()) null else categories.toString())
    }
}