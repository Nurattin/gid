package com.travel.gid.data.repository

import com.travel.gid.data.datasource.network.GidApi
import com.travel.gid.data.models.Gid
import com.travel.gid.domain.repository.GidListRepository
import retrofit2.Response
import javax.inject.Inject

class  GidListListRepositoryImpl @Inject constructor(private val api: GidApi): GidListRepository {
    override suspend fun getGidList(): Response<Gid> {
        return api.getGidList()
    }

}