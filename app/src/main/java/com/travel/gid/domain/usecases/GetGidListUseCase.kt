package com.travel.gid.domain.usecases

import com.travel.gid.data.models.Gid
import com.travel.gid.domain.repository.GidListRepository
import retrofit2.Response
import javax.inject.Inject

class GetGidListUseCase @Inject constructor(private val repo: GidListRepository) {
    suspend fun getGidList(): Response<Gid> = repo.getGidList()
}