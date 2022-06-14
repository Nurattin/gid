package com.travel.gid.domain.repository

import com.travel.gid.data.models.DirectionDetail
import com.travel.gid.data.models.Tour
import retrofit2.Response

interface TourByCategoriesRepository {
    suspend fun getToursByCategories(id: Long): Response<Tour>
}