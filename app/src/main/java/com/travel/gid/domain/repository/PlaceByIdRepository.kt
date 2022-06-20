package com.travel.gid.domain.repository

import com.travel.gid.data.models.Place
import retrofit2.Response

interface PlaceByIdRepository {
    suspend fun getPlaceById(id: Long): Response<Place>
}