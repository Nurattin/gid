package com.travel.gid.domain.repository

import com.travel.gid.data.models.Direction
import com.travel.gid.data.result.Result

interface DirectionListRepository {
    suspend fun getDirectionsList(): Result<Direction>
}