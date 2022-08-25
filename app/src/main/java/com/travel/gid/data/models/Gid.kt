package com.travel.gid.data.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Gid(
    @SerializedName("data")
    val data: List<GidDetail>
)


@Serializable
data class GidDetail(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("number")
    val number: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("detailImages")
    val detailImages: List<String>,
    @SerializedName("averageCheck")
    val averageCheck: Int,
    @SerializedName("city")
    val city: City,
)
