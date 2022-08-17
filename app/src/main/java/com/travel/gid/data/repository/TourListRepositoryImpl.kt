package com.travel.gid.data.repository

import com.travel.gid.data.datasource.network.GidApi
import com.travel.gid.data.di.IoDispatcher
import com.travel.gid.data.models.Tour
import com.travel.gid.data.result.Result
import com.travel.gid.domain.repository.TourListRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class TourListRepositoryImpl @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    var api: GidApi
) :
    TourListRepository {
    override suspend fun getTourList(
        priceFrom: Int?,
        priceTo: Int?,
        categories: List<Int>?,
        orderByPrice: String?,
    ): Result<Tour> =
        withContext(ioDispatcher) {
            return@withContext api.getTourList(
                priceFrom = priceFrom,
                priceTo = priceTo,
                categories = if (categories.isNullOrEmpty() || categories.contains(-1)) null else categories.toString(),
                orderByPrice = orderByPrice
            )
        }
}
