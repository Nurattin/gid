package com.travel.gid.data.datasource.network

import com.travel.gid.data.models.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface GidApi {

    @GET("tours/list")
    suspend fun getToursList(
    ): Response<Tour>

    @GET("places/list-category")
    suspend fun getCategoryList(
    ): Response<Categories>

    @GET("directions/list")
    suspend fun getDirectionsList(
    ): Response<Direction>

    @GET("directions/detail")
    suspend fun getDirectionDetail(
        @Query("id") id:Long
    ): Response<DirectionDetail>

    @GET("tours/detail")
    suspend fun getTourDetail(
        @Query("id") id:Long
    ): Response<TourDetail>

    @GET("tours/list")
    suspend fun getTourByCategories(
        @Query("categoryId") id: Long
    ): Response<Tour>
}

suspend fun <T> request(
    api: suspend () -> Response<T>
): ApiResponse {
    return try {
        val r = api()
        val data = r.body()
        if (r.isSuccessful) {
            ApiResponse.Result(data)
        } else {
            ApiResponse.Error("Ошибка", r.message())
        }
    } catch (e: Exception) {
        ApiResponse.Error("Ошибка", e.toString())
    }
}

sealed class ApiResponse {
    data class Error(val msg: String, val desc: String): ApiResponse()
    data class Result<out T>(val data: T): ApiResponse()
}