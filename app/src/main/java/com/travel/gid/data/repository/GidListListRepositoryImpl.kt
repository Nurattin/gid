package com.travel.gid.data.repository

import com.travel.gid.data.datasource.network.GidApi
import com.travel.gid.data.di.IoDispatcher
import com.travel.gid.data.models.Gid
import com.travel.gid.domain.repository.GidListRepository
import com.travel.gid.data.result.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GidListListRepositoryImpl @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val api: GidApi
) : GidListRepository {

    override suspend fun getGidList(): Result<Gid> = withContext(ioDispatcher) {
        return@withContext api.getGidList()
    }
}