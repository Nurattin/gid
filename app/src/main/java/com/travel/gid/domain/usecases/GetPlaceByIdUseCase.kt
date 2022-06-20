package com.travel.gid.domain.usecases

import com.travel.gid.data.models.Place
import com.travel.gid.domain.repository.PlaceByIdRepository
import retrofit2.Response
import javax.inject.Inject

class GetPlaceByIdUseCase @Inject constructor(private val repo: PlaceByIdRepository) {
    suspend fun getPlaceById(id: Long): Response<Place> = repo.getPlaceById(id)
}