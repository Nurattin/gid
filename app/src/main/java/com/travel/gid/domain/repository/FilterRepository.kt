package com.travel.gid.domain.repository

import com.travel.gid.data.models.Filters
import retrofit2.Response

interface FilterRepository {
    suspend fun getFilterParams(): Response<Filters>
}