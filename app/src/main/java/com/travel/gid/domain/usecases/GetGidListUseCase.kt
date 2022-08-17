package com.travel.gid.domain.usecases

import com.travel.gid.domain.repository.GidListRepository
import javax.inject.Inject

class GetGidListUseCase @Inject constructor(
    private val repo: GidListRepository
) {
    suspend operator fun invoke() = repo.getGidList()
}