package com.travel.gid.domain.usecases

import com.travel.gid.data.models.Filters
import com.travel.gid.data.result.Result
import com.travel.gid.domain.repository.FilterRepository
import retrofit2.Response
import javax.inject.Inject

class GetFilterUseCase @Inject constructor(
    private val repo: FilterRepository
) {
    suspend operator fun invoke(): Result<Filters> = repo.getFilterParams()

}