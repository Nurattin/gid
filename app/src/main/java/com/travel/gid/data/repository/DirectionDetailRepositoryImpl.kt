package com.travel.gid.data.repository

import com.travel.gid.data.datasource.network.GidApi
import com.travel.gid.data.models.DirectionDetail
import com.travel.gid.domain.repository.DirectionDetailRepository
import retrofit2.Response
import javax.inject.Inject

class DirectionDetailRepositoryImpl  @Inject constructor(private val api: GidApi): DirectionDetailRepository {

    override suspend fun getDirectionDetail(id: Long): Response<DirectionDetail> = api.getDirectionDetail(id)


}