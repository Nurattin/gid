package com.travel.gid.data.repository

import com.travel.gid.data.datasource.network.GidApi
import com.travel.gid.data.di.IoDispatcher
import com.travel.gid.data.models.Events
import com.travel.gid.domain.repository.EventListRepository
import com.travel.gid.data.result.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class EventListRepositoryImpl @Inject constructor(
    private val api: GidApi,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
): EventListRepository {
    override suspend fun getEventList(): Result<Events> {
        return withContext(ioDispatcher){
            return@withContext api.getEventList()
        }
    }
}