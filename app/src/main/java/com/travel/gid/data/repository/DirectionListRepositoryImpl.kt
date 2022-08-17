package com.travel.gid.data.repository

import com.travel.gid.data.datasource.network.GidApi
import com.travel.gid.data.di.IoDispatcher
import com.travel.gid.data.models.Direction
import com.travel.gid.domain.repository.DirectionListRepository
import com.travel.gid.data.result.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DirectionListRepositoryImpl @Inject constructor(
    private val api: GidApi,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : DirectionListRepository {
    override suspend fun getDirectionsList(): Result<Direction> = withContext(ioDispatcher) {
        return@withContext api.getDirectionsList()
    }
}