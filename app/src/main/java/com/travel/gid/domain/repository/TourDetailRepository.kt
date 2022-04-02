package com.travel.gid.domain.repository

import com.travel.gid.data.models.TourDetail
import retrofit2.Response

interface TourDetailRepository {
    suspend fun getTourDetail(id: Long): Response<TourDetail>
}