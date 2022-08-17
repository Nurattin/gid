package com.travel.gid.domain.repository

import com.travel.gid.data.models.Events
import com.travel.gid.data.result.Result

interface EventListRepository {
    suspend fun getEventList(): Result<Events>
}