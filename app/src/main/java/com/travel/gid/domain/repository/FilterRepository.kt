package com.travel.gid.domain.repository

import com.travel.gid.data.models.Filters
import com.travel.gid.data.result.Result
import retrofit2.Response

interface FilterRepository {
    suspend fun getFilterParams(): Result<Filters>
}