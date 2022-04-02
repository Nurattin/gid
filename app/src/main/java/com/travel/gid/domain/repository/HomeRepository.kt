package com.travel.gid.domain.repository

import com.travel.gid.data.Resource
import com.travel.gid.data.models.Direction
import com.travel.gid.data.models.Tour
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface HomeRepository {
    suspend fun getTours(): Response<Tour>
    suspend fun getTour(): Flow<Resource<Direction>>
    suspend fun getDirections(): Response<Direction>
}