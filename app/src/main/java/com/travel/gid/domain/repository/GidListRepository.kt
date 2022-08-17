package com.travel.gid.domain.repository

import com.travel.gid.data.models.Gid
import com.travel.gid.data.result.Result


interface GidListRepository {
    suspend fun getGidList(): Result<Gid>
}