package com.travel.gid.domain.repository

import com.travel.gid.data.models.Gid
import retrofit2.Response


interface GidListRepository {
    suspend fun getGidList(): Response<Gid>
}