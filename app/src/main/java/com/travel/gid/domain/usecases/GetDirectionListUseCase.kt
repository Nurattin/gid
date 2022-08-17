package com.travel.gid.domain.usecases

import com.travel.gid.data.models.Direction
import com.travel.gid.data.result.Result
import com.travel.gid.domain.repository.DirectionListRepository
import javax.inject.Inject

class GetDirectionListUseCase @Inject constructor(private val repo: DirectionListRepository) {
    suspend operator fun invoke(): Result<Direction> = repo.getDirectionsList()
}