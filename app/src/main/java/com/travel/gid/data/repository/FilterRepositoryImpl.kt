package com.travel.gid.data.repository

import com.travel.gid.data.datasource.network.GidApi
import com.travel.gid.data.di.IoDispatcher
import com.travel.gid.data.models.Filters
import com.travel.gid.data.result.Result
import com.travel.gid.domain.repository.FilterRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class FilterRepositoryImpl @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    var api: GidApi

) : FilterRepository {
    override suspend fun getFilterParams(): Result<Filters> = withContext(ioDispatcher) {
        return@withContext api.getFilterParams()
    }
}