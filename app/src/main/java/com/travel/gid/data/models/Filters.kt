package com.travel.gid.data.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Filters(
    @SerializedName("data")
    val data: FilterParameters
)

@Serializable
data class FilterParameters(
    @SerializedName("categories")
    val listCategories: MutableList<Categories>,
    @SerializedName("startCityId")
    val startCityId: List<StartCityId>,
    @SerializedName("peopleCount")
    val peopleCount: PeopleCount,
    @SerializedName("price")
    val price: Price,
//    @SerializedName("tourDates")
//    val tourDates: List<TourDates>,
)

@Serializable
data class Categories(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    var enable: Boolean = false
)

@Serializable
data class Price(
    @SerializedName("priceFrom")
    val priceFrom: Int,
    @SerializedName("priceTo")
    val priceTo: Int
)

@Serializable
data class StartCityId(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String
)

@Serializable
data class PeopleCount(
    @SerializedName("peopleCountFrom")
    val peopleCountFrom: Int,
    @SerializedName("peopleCountTo")
    val peopleCountTo: Int
)

@Serializable
data class TourDates(
    @SerializedName("startAt")
    val startAt: Int,
    @SerializedName("endAt")
    val endAt: Int
)