package com.travel.gid.domain.usecases

import com.travel.gid.domain.repository.EventListRepository
import javax.inject.Inject

class GetEventListUseCase @Inject constructor(
    private val repo: EventListRepository
) {
    suspend operator fun invoke() = repo.getEventList()
}