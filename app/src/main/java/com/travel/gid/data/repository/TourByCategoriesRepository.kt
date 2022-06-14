package com.travel.gid.data.repository

import com.travel.gid.data.datasource.network.GidApi
import com.travel.gid.data.models.Tour
import com.travel.gid.data.models.TourDetail
import com.travel.gid.domain.repository.TourByCategoriesRepository
import com.travel.gid.domain.repository.TourDetailRepository
import retrofit2.Response
import javax.inject.Inject

class TourByCategoriesRepository @Inject constructor(var api: GidApi): TourByCategoriesRepository {
    override suspend fun getToursByCategories(id: Long): Response<Tour> = api.getTourByCategories(id)
}