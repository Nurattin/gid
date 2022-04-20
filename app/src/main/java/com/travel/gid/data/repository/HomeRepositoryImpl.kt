package com.travel.gid.data.repository

import com.travel.gid.data.Resource
import com.travel.gid.data.datasource.network.GidApi
import com.travel.gid.data.models.Direction
import com.travel.gid.data.models.Tour
import com.travel.gid.data.models.TourData
import com.travel.gid.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class HomeRepositoryImpl @Inject constructor(private val api: GidApi, private val ioDispatcher: CoroutineContext): HomeRepository {

    override suspend fun getTours(): Response<Tour> = api.getToursList()

    override suspend fun getDirection(): Response<Direction> {
        return api.getDirectionsList()
    }

    suspend fun requestTour(): Resource<Direction> {
        return when (val response = processCall(api::getDirectionsList)) {
            is Direction -> {
                Resource.Success(data = response)
            }
            else -> {
                Resource.DataError()
            }
        }
    }

    private suspend fun processCall(responseCall: suspend () -> Response<*>): Any? {
        return try {
            val response = responseCall.invoke()
            val responseCode = response.code()
            if (response.isSuccessful) {
                response.body()
            } else {
                responseCode
            }
        } catch (e: IOException) {
            1
        }
    }

    override suspend fun getDirections(): Response<Direction> = api.getDirectionsList()

}