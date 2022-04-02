package com.travel.gid.domain.repository

import com.travel.gid.data.models.DirectionDetail
import retrofit2.Response

interface DirectionDetailRepository {
    suspend fun getDirectionDetail(id: Long): Response<DirectionDetail>
}