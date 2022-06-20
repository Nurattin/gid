package com.travel.gid.domain.repository

import com.travel.gid.data.models.Filters
import com.travel.gid.data.models.Direction
import com.travel.gid.data.models.Tour
import retrofit2.Response

interface HomeRepository {
    suspend fun getTours(): Response<Tour>
    suspend fun getDirection(): Response<Direction>
    suspend fun getDirections(): Response<Direction>
}