package com.travel.gid.data.repository

import com.travel.gid.data.datasource.network.GidApi
import com.travel.gid.data.models.TourDetail
import com.travel.gid.domain.repository.TourDetailRepository
import retrofit2.Response
import javax.inject.Inject

class TourDetailRepositoryImpl @Inject constructor(var api: GidApi): TourDetailRepository {
    override suspend fun getTourDetail(id: Long): Response<TourDetail> = api.getTourDetail(id)
}