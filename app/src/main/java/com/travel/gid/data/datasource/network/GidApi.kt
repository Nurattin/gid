package com.travel.gid.data.datasource.network

import com.travel.gid.data.models.*
import com.travel.gid.data.result.Result
import retrofit2.Response
import retrofit2.http.*


interface GidApi {

    @GET("tours/list-filter-parameters")
    suspend fun getFilterParams(
    ): Result<Filters>

    @GET("directions/list")
    suspend fun getDirectionsList(
    ): Result<Direction>

    @GET("directions/detail")
    suspend fun getDirectionDetail(
        @Query("id") id: Long
    ): Response<DirectionDetail>

    @GET("tours/detail")
    suspend fun getTourDetail(
        @Query("id") id: Long
    ): Response<TourDetail>

    @POST("tours/list")
    suspend fun getToursList(
    ): Response<Tour>

    @GET("places/detail")
    suspend fun getPlaceById(@Query("id") id: Long): Response<Place>

    @POST("guides/list")
    suspend fun getGidList(): Result<Gid>

    @POST("events/list")
    suspend fun getEventList(): Result<Events>

    @FormUrlEncoded
    @POST("tours/list")
    suspend fun getTourList(
        @Field("priceFrom")priceFrom: Int?,
        @Field("priceTo")priceTo: Int?,
        @Field("categories") categories: String?,
        @Field("orderByPrice") orderByPrice: String?,
    ): Result<Tour>
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
    data class Error(val msg: String, val desc: String) : ApiResponse()
    data class Result<out T>(val data: T) : ApiResponse()
}