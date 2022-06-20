package com.travel.gid.data.repository

import com.travel.gid.data.datasource.network.GidApi
import com.travel.gid.data.models.Place
import com.travel.gid.domain.repository.PlaceByIdRepository
import retrofit2.Response
import javax.inject.Inject

class PlaceByIdRepositoryImpl @Inject constructor(val api: GidApi): PlaceByIdRepository {
    override suspend fun getPlaceById(id: Long): Response<Place> {
        return api.getPlaceById(id)
    }
}