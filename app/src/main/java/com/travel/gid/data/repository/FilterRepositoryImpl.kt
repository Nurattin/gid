package com.travel.gid.data.repository

import com.travel.gid.data.datasource.network.GidApi
import com.travel.gid.data.models.Filters
import com.travel.gid.domain.repository.FilterRepository
import retrofit2.Response
import javax.inject.Inject

class FilterRepositoryImpl @Inject constructor(var api: GidApi) : FilterRepository {
    override suspend fun getFilterParams(): Response<Filters> {
        return api.getFilterParams()
    }

}