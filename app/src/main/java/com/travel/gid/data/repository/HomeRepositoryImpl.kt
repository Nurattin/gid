package com.travel.gid.data.repository

import com.travel.gid.data.datasource.network.GidApi
import com.travel.gid.data.models.*
import com.travel.gid.domain.repository.HomeRepository
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(private val api: GidApi): HomeRepository {

    override suspend fun getTours(): Response<Tour> = api.getToursList()

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
}